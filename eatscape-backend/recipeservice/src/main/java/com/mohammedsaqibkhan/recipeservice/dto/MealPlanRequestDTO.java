package com.mohammedsaqibkhan.recipeservice.dto;

import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import lombok.Data;

import java.util.Map;

@Data
public class MealPlanRequestDTO {
    private String date;
    private Map<String, Recipe> mealPlan;

    // Getters and setters
}

