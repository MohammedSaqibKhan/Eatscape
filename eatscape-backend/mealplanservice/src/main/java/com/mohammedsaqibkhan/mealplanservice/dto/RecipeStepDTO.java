package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

@Data
public class RecipeStepDTO {
    private Long id;
    private Long recipeId; // Add recipeId
    private int stepNumber;
    private String stepDescription;
}

