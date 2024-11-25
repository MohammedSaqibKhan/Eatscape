package com.mohammedsaqibkhan.recipeservice.dto;

import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalInfoDTO {
    private Long id;
    private Recipe recipe;

    private double calories; // in kcal
    private double protein; // in grams
    private double carbs; // in grams
    private double fats; // in grams
}
