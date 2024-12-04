package com.mohammedsaqibkhan.nutritionservice.entity;

import jakarta.persistence.*;



@Entity
public class Nutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int attrId;
    private double value;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    // Getters and setters
}



