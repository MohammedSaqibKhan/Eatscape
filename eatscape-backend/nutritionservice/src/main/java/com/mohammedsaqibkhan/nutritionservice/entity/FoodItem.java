package com.mohammedsaqibkhan.nutritionservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private String brandName;
    private String consumedAt;
    private String item;
    private String quantity;
    private String foodGroup;
    private String photoThumbUrl;
    private String photoHighResUrl;
    private boolean isRawFood;  // Based on metadata
    private String ndbNo;
    private int source; // Source of the data (Nutritionix)

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL)
    private List<Nutrient> nutrients;

    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL)
    private List<FoodMeasure> foodMeasures;

    @ManyToOne
    @JoinColumn(name = "meal_type_id")
    private MealType mealType;

    // Getters and setters
}

