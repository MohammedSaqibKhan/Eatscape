package com.mohammedsaqibkhan.nutritionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammedsaqibkhan.nutritionservice.dto.IngredientDTO;
import com.mohammedsaqibkhan.nutritionservice.dto.NutritionixResponseDTO;
import com.mohammedsaqibkhan.nutritionservice.processor.NutritionixResponseProcessor;
import com.mohammedsaqibkhan.nutritionservice.service.NutritionixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionixController {

    @Autowired
    private NutritionixService nutritionixService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/analyze")
    public NutritionixResponseDTO analyzeNutrition(@RequestBody List<IngredientDTO> ingredients) {
        String response = nutritionixService.getNutritionalData(ingredients);
        try {
            NutritionixResponseDTO nutritionixResponse = objectMapper.readValue(response, NutritionixResponseDTO.class);
            return NutritionixResponseProcessor.processResponse(nutritionixResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Nutritionix response: " + e.getMessage());
        }
    }
}

