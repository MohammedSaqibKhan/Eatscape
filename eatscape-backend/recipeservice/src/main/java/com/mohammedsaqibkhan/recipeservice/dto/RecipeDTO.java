package com.mohammedsaqibkhan.recipeservice.dto;

import com.mohammedsaqibkhan.recipeservice.entity.*;
import com.mohammedsaqibkhan.recipeservice.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private String name;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;

    private DietType dietType;
    private MealType mealType;

    private List<RecipeIngredient> ingredients;
    private NutritionalInfo nutritionalInfo;
    private List<RecipeStep> steps;

    private String imageUrl;
    private String videoUrl;
    private DifficultyLevel difficultyLevel;

    private boolean isFavorite;
    private int views;
    private int favorites;

    private List<String> tags;
    private String cuisine;
}
