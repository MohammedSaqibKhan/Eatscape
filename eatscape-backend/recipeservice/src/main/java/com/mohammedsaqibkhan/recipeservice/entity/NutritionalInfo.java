package com.mohammedsaqibkhan.recipeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Recipe recipe;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "calories")
    private double calories;

    @Column(name = "carbs", nullable = false) // Add carbs field
    private double carbs;

    @Column(name = "total_fat")
    private double totalFat;

    @Column(name = "saturated_fat")
    private double saturatedFat;

    @Column(name = "cholesterol")
    private double cholesterol;

    @Column(name = "sodium")
    private double sodium;

    @Column(name = "total_carbohydrates")
    private double totalCarbohydrates;

    @Column(name = "dietary_fiber")
    private double dietaryFiber;

    @Column(name = "sugars")
    private double sugars;

    @Column(name = "protein")
    private double protein;

    @Column(name = "potassium")
    private double potassium;

    @Column(name = "phosphorus")
    private double phosphorus;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "serving_qty")
    private double servingQty;

    @Column(name = "serving_unit")
    private String servingUnit;

    @Column(name = "serving_weight_grams")
    private double servingWeightGrams;

    @Column(name = "source")
    private String source;

    @Column(name = "is_raw_food")
    private boolean isRawFood;

    @Column(name = "ndb_no")
    private String ndbNo;

    @Lob
    @Column(name = "full_nutrients", columnDefinition = "TEXT")
    private String fullNutrients; // Serialized JSON or a string representation
}
