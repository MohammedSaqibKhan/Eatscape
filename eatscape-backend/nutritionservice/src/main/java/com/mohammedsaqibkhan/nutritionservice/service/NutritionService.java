package com.mohammedsaqibkhan.nutritionservice.service;

import com.mohammedsaqibkhan.nutritionservice.model.Ingredient;
import com.mohammedsaqibkhan.nutritionservice.model.NutritionRequest;
import com.mohammedsaqibkhan.nutritionservice.model.NutritionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NutritionService {

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.app.key}")
    private String appKey;

    private final RestTemplate restTemplate;

    public NutritionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Update to accept NutritionRequest and format ingredients for Nutritionix API
    public NutritionResponse analyzeIngredients(NutritionRequest request) {
        // Convert ingredients to a comma-separated string
        String ingredients = formatIngredients(request.getIngredients());

        // Prepare API request to Nutritionix
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.set("Content-Type", "application/json");

        // Request body with ingredients and timezone
        String requestJson = "{ \"query\": \"" + ingredients + "\", \"timezone\": \"Asia/Kolkata\" }";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // Send request and retrieve response
        ResponseEntity<NutritionResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, NutritionResponse.class);

        return response.getBody();
    }

    private String formatIngredients(List<Ingredient> ingredients) {
        // Join the ingredient names into a comma-separated string for the Nutritionix API
        return ingredients.stream()
                .map(ingredient -> ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName())
                .collect(Collectors.joining(", "));
    }

    public String getSuggestions() {
        return "";
    }
}
