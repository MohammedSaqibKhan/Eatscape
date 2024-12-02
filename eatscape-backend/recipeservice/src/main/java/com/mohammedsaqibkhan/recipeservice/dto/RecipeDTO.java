package com.mohammedsaqibkhan.recipeservice.dto;

import com.mohammedsaqibkhan.recipeservice.entity.*;
import com.mohammedsaqibkhan.recipeservice.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private DifficultyLevel difficultyLevel;

    private boolean isFavorite;
    private int views;
    private int favorites;

    private List<String> tags;
    private String cuisine;

    // New fields added from Recipe entity
    private Double averageRating;  // Average rating (calculated)
    private int totalRatings;      // Total number of ratings
    private LocalDate date;        // Date associated with the recipe
    private boolean isDeleted;     // Flag to check if deleted
}