package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.NutritionalInfo;
import com.mohammedsaqibkhan.recipeservice.entity.RecipeIngredient;
import com.mohammedsaqibkhan.recipeservice.mapper.NutritionalInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionalInfoServiceImpl implements NutritionalInfoService {
//    private final ExternalNutritionApiClient nutritionApiClient;
    private final NutritionalInfoMapper nutritionalInfoMapper;

//    @Override
//    public NutritionalInfo calculateNutrition(List<RecipeIngredient> ingredients) {
//        // Call external API for nutritional calculations
//        NutritionalInfo nutritionalInfo = nutritionApiClient.calculateNutritionalInfo(ingredients);
//
//        // Return the result as a DTO
//        return nutritionalInfo;
//    }
}
