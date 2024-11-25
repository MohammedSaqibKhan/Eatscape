package com.mohammedsaqibkhan.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {
    private Long id;
    private RecipeDTO recipe;
    private String IngredientName;
    private String category;
    private String quantity;
    private String unit;
}
