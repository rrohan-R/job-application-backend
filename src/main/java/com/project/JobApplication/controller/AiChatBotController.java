package com.project.JobApplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class AiChatBotController {

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/"
                    + "gemini-3.8-flash:generateContent";

    private final String API_KEY;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AiChatBotController(
            @Value("${api.secret}") String apiKey) {

        this.API_KEY = apiKey;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping
    public ResponseEntity<String> askGemini(
            @RequestBody Map<String, String> request) {

        String question = request.get("question");

        if (question == null || question.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Question cannot be empty");
        }

        try {

            // Gemini request body
            Map<String, Object> body = Map.of(
                    "contents", new Object[]{
                            Map.of(
                                    "parts", new Object[]{
                                            Map.of(
                                                    "text",
                                                    question
                                            )
                                    }
                            )
                    }
            );

            // HTTP headers
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", API_KEY);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // Call Gemini
            ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL, entity, String.class);

            // Parse Gemini response
            JsonNode root = objectMapper.readTree(response.getBody());

            String answer =
                    root.path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

            return ResponseEntity.ok(answer);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while communicating with Gemini: "
                            + e.getMessage());
        }
    }
}