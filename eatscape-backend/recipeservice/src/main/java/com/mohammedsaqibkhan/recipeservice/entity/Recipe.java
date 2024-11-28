package com.mohammedsaqibkhan.recipeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mohammedsaqibkhan.recipeservice.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    // Rating fields
    @ElementCollection
    @JsonIgnore // Optional: Only expose averageRating in the response if needed
    private List<Double> ratings; // Individual ratings (e.g., from 1 to 5 stars)

    private Double averageRating; // Calculated average rating

    private int totalRatings;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public int getTotalRatings() {
        return ratings != null ? ratings.size() : 0;
    }


    /**
     * Add a new rating to the recipe and update the average rating.
     *
     * @param newRating the rating to add (e.g., 1-5)
     */
    public void addRating(double newRating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(newRating);
        updateAverageRating();
    }


    /**
     * Calculate and update the average rating.
     */
    private void updateAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            this.averageRating = null;
        } else {
            this.averageRating = ratings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            this.averageRating = Math.round(this.averageRating * 2) / 2.0;
        }
    }
}