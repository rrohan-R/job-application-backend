package com.project.JobApplication.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/wolfram")
public class AiChatBotController {

    private static final String WOLFRAM_API_BASE = "https://api.wolframalpha.com/v1/spoken";
    private final String APP_ID;

    public AiChatBotController(@Value("${api.secret}") String appId) {
        this.APP_ID = appId;
    }

    @GetMapping
    public ResponseEntity<String> askWolfram(@RequestParam String question) {
        String url = UriComponentsBuilder.fromHttpUrl(WOLFRAM_API_BASE)
                .queryParam("appid", APP_ID)
                .queryParam("i", question)
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        try {
            String result = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(result);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error: " + e.getResponseBodyAsString());
        }
    }
}
