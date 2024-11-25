package com.mohammedsaqibkhan.nutritionservice.model;

import lombok.Data;

import java.util.List;

@Data
public class NutritionRequest {
    private List<Ingredient> ingredients;
    private Ingredient ingredient;

}
