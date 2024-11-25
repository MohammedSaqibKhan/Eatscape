package com.mohammedsaqibkhan.recipeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mohammedsaqibkhan.recipeservice.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int prepTime; // in minutes
    private int cookTime; // in minutes
    private int servings; // number of servings

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_type_id", nullable = false)
    private DietType dietType; // Example: Vegan, Gluten-Free, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_type_id", nullable = false)
    private MealType mealType; // Example: Breakfast, Lunch, etc.

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecipeIngredient> ingredients; // Linked by recipeId in RecipeIngredient

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private NutritionalInfo nutritionalInfo; // Optional: Nutritional details

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecipeStep> steps; // Step-by-step instructions

    private String imageUrl; // URL for recipe image

    private String videoUrl; // Optional: Video tutorial URL

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel; // EASY, MEDIUM, HARD

    private boolean isFavorite; // User marked as favorite

    private int views; // Number of views
    private int favorites; // Number of favorites

    @ElementCollection
    private List<String> tags; // Tags like "Quick," "Kids-Friendly," etc.

    private String cuisine; // Cuisine type, e.g., Italian, Indian, etc.
}