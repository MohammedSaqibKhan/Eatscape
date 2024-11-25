package com.mohammedsaqibkhan.mealservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name should not be empty")
    private String name;
    private String description;
    private double calories;
    private double fat;
    private double carbs;
    private double protein;
    private String ingredients;
    private String dietType; // e.g., vegetarian, vegan, keto, etc.
}
