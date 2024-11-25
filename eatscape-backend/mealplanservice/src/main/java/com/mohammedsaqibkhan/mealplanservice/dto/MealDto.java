package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class MealDto {
    private Long id;
    private String name;
    private String description;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;
    private String ingredients;
    private String dietType;  // e.g., vegetarian, vegan, keto, etc.


}
