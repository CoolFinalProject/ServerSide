package server.helper;

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

@Service
public class OpenAiService {


    @Value("${openai.api.key:#{null}}")
    private String apiKeyFromProps;

    private final ObjectMapper om = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();


    private String apiKey() {
        String k = apiKeyFromProps != null ? apiKeyFromProps : System.getenv("OPENAI_API_KEY");
        if (k == null || k.isBlank()) {
            throw new IllegalStateException("Missing OPENAI_API_KEY (property or env var).");
        }
        return k;
    }


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
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(root)))
                    .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() != 200) {
                throw new RuntimeException("OpenAI error " + res.statusCode() + ": " + res.body());
            }

            JsonNode tree = om.readTree(res.body());
            JsonNode content = tree.path("choices").path(0).path("message").path("content");
            if (content.isMissingNode() || content.isNull()) {
                throw new RuntimeException("OpenAI response missing content: " + res.body());
            }
            return content.asText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("OpenAI call failed", e);
        }
    }


    public String summarizeNeutral(String text) {
        String prompt = """
Act as an impartial wire-service editor.

Your task is to reconstruct the article as a collection of verifiable facts, not as a condensed version of the journalist's narrative.

Output the summary in the exact same language as the original article.

Heavily paraphrase the source to avoid copyright infringement. Never copy phrases or sentences directly from the article. Do not reproduce more than three consecutive words from the source unless they are proper nouns, official organization names, technical terms, or explicitly attributed direct quotations.

Before writing the summary, mentally filter the article and discard any information that is not directly factual.

Specifically, DO NOT include:

- opinions
- interpretations
- speculation
- predictions
- expectations
- assumptions
- inferred motivations
- inferred consequences
- emotional language
- rhetorical questions
- narrative framing
- evaluative adjectives
- unsupported comparisons
- journalist conclusions
- statements about what readers, consumers, voters, experts, or the public may think or feel, unless explicitly attributed to a named source.

If a statement cannot be verified from observable facts stated in the article, remove it.

Treat statements about possible future events, outcomes, consequences, or conditions as non-factual unless they are explicitly attributed to an identified source.

This includes statements such as:
- may, might, could, likely, unlikely
- expected to
- if... then...
- depends on...
- may lead to...
- may become...
- may improve...
- may reduce...
- will determine...
- would result in...

Remove these statements unless they are explicitly presented as the opinion, prediction, or assessment of a named person or organization.

If a sentence mixes facts with interpretation, keep only the factual portion.

Do not rewrite subjective statements into more neutral wording. Instead, remove the subjective portion entirely and retain only the verifiable facts.

If a claim is explicitly attributed to an identified person or organization, preserve the attribution.

Do not preserve the journalist's framing, emphasis, or implied message.

When uncertain whether a statement is factual or interpretive, omit it.

Write the summary in standard paragraphs only (no bullet points or markdown).

Use an inverted pyramid structure:
- begin with the most important verified facts;
- continue with supporting factual details;
- finish with relevant background if necessary.

Produce a comprehensive summary of approximately 3–4 paragraphs that contains only verifiable information.

Text:
""" + text;

        return chat(prompt);
    }

    public List<ArticleCategory> classifyCategories(String title, String description, String url) {
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

        String result = chat(prompt).trim().toUpperCase();

        List<ArticleCategory> categories = new ArrayList<>();

        for (String value : result.split(",")) {
            try {
                categories.add(ArticleCategory.valueOf(value.trim()));
            } catch (Exception ignored) {
            }
        }

        if (categories.isEmpty()) {
            categories.add(ArticleCategory.GENERAL);
        }

        return categories;
    }
}