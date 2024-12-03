package com.mohammedsaqibkhan.mealplanservice.dto;

import com.mohammedsaqibkhan.mealplanservice.enums.DifficultyLevel;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private DietTypeDTO dietType;
    private MealTypeDTO mealType;
    private List<RecipeIngredientDTO> ingredients;
    private NutritionalInfoDTO nutritionalInfo;
    private List<RecipeStepDTO> steps;
    private String imageUrl;
    private String videoUrl;
    private DifficultyLevel difficultyLevel;
    private boolean isFavorite;
    private int views;
    private int favorites;
    private List<String> tags;
    private String cuisine;
    private List<Double> ratings;
    private Double averageRating;
    private int totalRatings;
    private LocalDate date;
    private boolean isDeleted;
}

