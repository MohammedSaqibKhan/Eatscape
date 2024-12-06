package com.mohammedsaqibkhan.recipeservice.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ImageService {

    @Value("${pexels.api.key}")
    private String pexelsApiKey;

    private final RestTemplate restTemplate;

    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to get image from Pexels API based on ingredient name
    public String getIngredientImage(String ingredientName) {
        // Construct the URL for the Pexels API request
        String url = UriComponentsBuilder.fromHttpUrl("https://api.pexels.com/v1/search")
                .queryParam("query", ingredientName)
                .queryParam("per_page", "1") // Limit to 1 result
                .toUriString();

        try {
            // Make the API call using the Pexels API key for authentication
            String response = restTemplate.exchange(url, HttpMethod.GET,
                    new HttpEntity<>(createHeaders()), String.class).getBody();

            // Parse the JSON response from Pexels API
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray photos = jsonResponse.getJSONArray("photos");

            // If photos are found, return the image URL from the first photo
            if (photos.length() > 0) {
                JSONObject firstPhoto = photos.getJSONObject(0);
                return firstPhoto.getJSONObject("src").getString("medium"); // 'medium' size image
            } else {
                // Return a fallback image if no images are found
                return "https://www.pexels.com/photo/no-image-available/";
            }

        } catch (Exception e) {
            // Log any errors and return a fallback image URL
            e.printStackTrace();
            return "https://www.pexels.com/photo/no-image-available/";
        }
    }

    // Method to create HTTP headers for Pexels API authentication
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", pexelsApiKey);
        return headers;
    }
}