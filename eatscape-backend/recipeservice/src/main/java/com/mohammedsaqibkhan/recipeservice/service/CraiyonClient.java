package com.mohammedsaqibkhan.recipeservice.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CraiyonClient {

    private final RestTemplate restTemplate;

    public CraiyonClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateImage(String prompt) {
        // Craiyon's unofficial endpoint
        String url = "https://api.craiyon.com/generate";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        headers.set("Accept", "application/json");

        // Body
        Map<String, String> body = new HashMap<>();
        body.put("prompt", prompt);

        // Wrap body and headers into an HttpEntity
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // Send POST request
        try {
            Map<String, Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            if (response != null && response.get("image_url") != null) {
                return response.get("image_url").toString();
            } else {
                System.err.println("Error: No image URL returned.");
                return null;
            }
        } catch (HttpStatusCodeException e) {
            System.err.println("Error generating image: " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            return null;
        }
    }
}