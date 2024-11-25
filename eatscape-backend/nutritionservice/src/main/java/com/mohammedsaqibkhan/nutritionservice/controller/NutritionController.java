package com.mohammedsaqibkhan.nutritionservice.controller;

import com.mohammedsaqibkhan.nutritionservice.model.NutritionRequest;
import com.mohammedsaqibkhan.nutritionservice.model.NutritionResponse;
import com.mohammedsaqibkhan.nutritionservice.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nutrition")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @PostMapping("/analyze")
    public ResponseEntity<NutritionResponse> analyzeNutrition(@RequestBody NutritionRequest request) {
        NutritionResponse response = nutritionService.analyzeIngredients(request);
        System.out.println(response.toString());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/suggest")
    public ResponseEntity<String> getDietarySuggestions() {
        String suggestions = nutritionService.getSuggestions();
        return ResponseEntity.ok(suggestions);
    }
}

