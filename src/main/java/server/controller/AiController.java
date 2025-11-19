package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.helper.OpenAiService;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final OpenAiService openAi;

    public AiController(OpenAiService openAi) {
        this.openAi = openAi;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String message) {
        return ResponseEntity.ok(openAi.chat(message));
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarize(@RequestBody String text) {
        return ResponseEntity.ok(openAi.summarizeNeutral(text));
    }
}

