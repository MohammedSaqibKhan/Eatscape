package com.mohammedsaqibkhan.recipeservice.dto;

import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeStepDTO {
    private Long id;

    private Recipe recipe;
    private int stepNumber;
    private String stepDescription; // Detailed step description
}
