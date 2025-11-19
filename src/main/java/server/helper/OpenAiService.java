package server.helper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

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
}