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
Act as an impartial wire service editor. Your task is to process the provided article and write a comprehensive, strictly neutral news summary. You must output the final summary in the exact same language as the original article text. To completely avoid copyright infringement, you must heavily paraphrase the original text. You are forbidden from copying phrases or sentences from the source material. Explain the facts using entirely new sentence structures and your own original wording. Do not reuse strings of more than three consecutive words from the original article, unless they are proper nouns, official entity names, or explicitly attributed direct quotes. Write entirely in standard paragraphs without using any bullet points, numbered lists, or markdown formatting. Strip away all editorializing, emotional language, and sensationalism. Report the core facts objectively without injecting moral judgments. Use an inverted pyramid style, starting with the most critical information in the opening paragraph, followed by supporting context in subsequent paragraphs. Provide a thorough summary that retains necessary nuance, aiming for a well-paced narrative of about 3 to 4 paragraphs.                            Text:
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