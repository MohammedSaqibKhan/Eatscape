package com.mohammedsaqibkhan.mealplanservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class MealPlanEaten {
    private Long recipeId;         // ID of the recipe
    private boolean eaten;         // Whether the recipe was eaten (true/false)
    private LocalDateTime eatenTime; // (Optional) Track when the recipe was eaten
}