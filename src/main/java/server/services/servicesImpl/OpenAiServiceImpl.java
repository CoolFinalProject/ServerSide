package server.services.servicesImpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import server.enums.ArticleCategory;
import server.services.OpenAiService;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    @Value("${openai.api.key:#{null}}")
    private String apiKeyFromProps;

    private final ObjectMapper om = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();



    private String apiKey() {
        String k = apiKeyFromProps != null
                ? apiKeyFromProps
                : System.getenv("OPENAI_API_KEY");

        if (k == null || k.isBlank()) {
            throw new IllegalStateException(
                    "Missing OPENAI_API_KEY (property or env var)."
            );
        }

        return k;
    }

    @Override
    public String chat(String userMessage) {
        try {
            ObjectNode root = om.createObjectNode();

            root.put("model", "gpt-4o-mini");
            root.put("temperature", 0);

            ArrayNode messages = root.putArray("messages");

            ObjectNode user = om.createObjectNode();
            user.put("role", "user");
            user.put("content", userMessage);

            messages.add(user);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.openai.com/v1/chat/completions"
                    ))
                    .header(
                            "Authorization",
                            "Bearer " + apiKey()
                    )
                    .header(
                            "Content-Type",
                            "application/json"
                    )
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    om.writeValueAsString(root)
                            )
                    )
                    .build();

            HttpResponse<String> res = http.send(
                    req,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (res.statusCode() != 200) {
                throw new RuntimeException(
                        "OpenAI error "
                                + res.statusCode()
                                + ": "
                                + res.body()
                );
            }

            JsonNode tree = om.readTree(res.body());

            JsonNode content = tree
                    .path("choices")
                    .path(0)
                    .path("message")
                    .path("content");

            if (content.isMissingNode() || content.isNull()) {
                throw new RuntimeException(
                        "OpenAI response missing content: "
                                + res.body()
                );
            }

            return content.asText();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(
                    "OpenAI call failed",
                    e
            );
        }
    }

    @Override
    public String summarizeNeutral(
            String text,
            String summaryPrompt
    ) {
        String prompt = summaryPrompt + """

Text:
""" + text;

        return chat(prompt);
    }

    @Override
    public String updateSummaryPrompt(
            String currentPrompt,
            String feedback
    ) {
        String prompt = """
You are updating a personalized prompt used to summarize news articles.

Current prompt:
%s

User feedback:
%s

Rewrite the current prompt so that it incorporates the user's feedback.

Important rules:
- Treat the user's feedback as an authoritative specification, not as a suggestion.
- Follow the user's feedback as precisely as possible.
- Preserve the exact meaning, intent, and logical strength of every explicit user requirement.
- Do not weaken, soften, approximate, generalize, reinterpret, normalize, or broaden explicit user requirements.
- Do not paraphrase a user constraint in a way that changes its meaning.
- Preserve measurable constraints accurately, including minimums, maximums, ranges, exact quantities, lengths, counts, and formatting requirements.
- If the feedback contains a strict condition, the resulting prompt must preserve an equally strict condition.
- If the new feedback conflicts with an older personalization preference, the newer feedback takes precedence.
- Preserve all other important existing instructions unless they directly conflict with the new feedback.
- Preserve the core requirement to produce factual and neutral summaries.
- Do not allow personalization to introduce factual distortion, unsupported claims, political bias, or removal of the core neutrality requirements.
- Your task is to integrate the user's instruction into the existing prompt, not to improve, normalize, or reinterpret the user's preference.
- The result must be a complete standalone prompt that can entirely replace the current prompt.
- Do not describe or explain the changes you made.
- Return only the updated prompt.
""".formatted(currentPrompt, feedback);

        return chat(prompt);
    }

    @Override
    public List<ArticleCategory> classifyCategories(
            String title,
            String description,
            String url
    ) {
        String prompt = """
                Classify the following news article into exactly one category:
                    GENERAL,
                    POLITICS,
                    MILITARY,
                    WAR,
                    WORLD,
                    BUSINESS,
                    TECHNOLOGY,
                    SCIENCE,
                    SPORTS,
                    HEALTH,
                    ENTERTAINMENT,
                    CELEBRITIES,
                    CULTURE,
                    FOOD,
                    TRAVEL,
                    LIFESTYLE,
                    EDUCATION,
                    ENVIRONMENT.

                Return one or two category names only.
                If two categories are appropriate, separate them with a comma.
                Examples:
                SPORTS
                TECHNOLOGY,BUSINESS
                GENERAL

            Title: %s
            Description: %s
            Url: %s
            """.formatted(title, description, url);

        String result = chat(prompt)
                .trim()
                .toUpperCase();

        List<ArticleCategory> categories =
                new ArrayList<>();

        for (String value : result.split(",")) {
            try {
                categories.add(
                        ArticleCategory.valueOf(
                                value.trim()
                        )
                );
            } catch (Exception ignored) {
            }
        }

        if (categories.isEmpty()) {
            categories.add(
                    ArticleCategory.GENERAL
            );
        }

        return categories;
    }
}