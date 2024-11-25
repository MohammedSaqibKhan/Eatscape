package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeIngredientDTO;
import com.mohammedsaqibkhan.recipeservice.dto.NutritionalInfoDTO;
import com.mohammedsaqibkhan.recipeservice.service.NutritionalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nutrition")
@RequiredArgsConstructor
public class NutritionalInfoController {
    private final NutritionalInfoService nutritionalInfoService;

    // Calculate nutritional info for a given list of ingredients
//    @PostMapping("/calculate")
//    public ResponseEntity<NutritionalInfoDTO> calculateNutrition(@RequestBody List<IngredientDTO> ingredients) {
//        return ResponseEntity.ok(nutritionalInfoService.calculateNutrition(ingredients));
//    }
}
