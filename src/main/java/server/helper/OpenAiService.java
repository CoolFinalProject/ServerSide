package server.helper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
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

    /** דוגמת עיבוד: סיכום טקסט קצר, ללא דעות. */
    public String summarizeNeutral(String text) {
        String prompt = """
                Summarize the following content in 3 bullet points, remove opinions/emotion, keep facts only.
                Text:
                """ + text;
        return chat(prompt);
    }

    public List<ArticleCategory> classifyCategories(String title, String description, String url) {
        String prompt = """
                Classify the following news article into exactly one category:
                GENERAL, POLITICS, WORLD, SECURITY, CRIME, LAW, BUSINESS, TECHNOLOGY, SCIENCE, HEALTH, SPORTS, ENTERTAINMENT, CELEBRITIES, CULTURE, FOOD, TRAVEL, LIFESTYLE, EDUCATION, ENVIRONMENT.

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