package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

@Data
public class RecipeIngredientDTO {
    private Long id;
    private Long recipeId; // Add recipeId
    private String ingredientName;
    private String category;
    private String quantity;
    private String unit;
}

