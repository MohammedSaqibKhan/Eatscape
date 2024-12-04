package com.mohammedsaqibkhan.nutritionservice.entity;

import jakarta.persistence.*;

@Entity
public class FoodMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double qty;
    private String measure;
    private double servingWeight;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    // Getters and setters
}

