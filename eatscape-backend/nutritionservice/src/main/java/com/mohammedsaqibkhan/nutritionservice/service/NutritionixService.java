package com.mohammedsaqibkhan.nutritionservice.service;

import com.mohammedsaqibkhan.nutritionservice.dto.IngredientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class NutritionixService {
    private static final Logger logger = LoggerFactory.getLogger(NutritionixService.class);


    private static final String BASE_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    private static final String APP_ID = "2a7d5bcd"; // Replace with your APP ID
    private static final String API_KEY = "57b5b2bbfd925b4e74a14839b6c9f862"; // Replace with your API Key

    public String getNutritionalData(List<IngredientDTO> ingredients) {
        // Construct the query string from the list of IngredientDTO
        String query = ingredients.stream()
                .map(ingredient -> ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getIngredientName())
                .collect(Collectors.joining(", "));

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", APP_ID);
        headers.set("x-app-key", API_KEY);
        headers.set("Content-Type", "application/json");

        // Prepare request body
        String requestBody = "{ \"query\": \"" + query + "\" }";

        // Build HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Call Nutritionix API
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Calling Nutritionix API for ingredients: {}", ingredients);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, entity, String.class);
        logger.info("Received response from Nutritionix");
        return response.getBody();
    }
}
