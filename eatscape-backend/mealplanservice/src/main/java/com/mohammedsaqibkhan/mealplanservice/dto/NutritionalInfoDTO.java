package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

@Data
public class NutritionalInfoDTO {
    private Long id;
    private Long recipeId; // Add recipeId
    private double calories;
    private double protein;
    private double carbs;
    private double fats;
}

