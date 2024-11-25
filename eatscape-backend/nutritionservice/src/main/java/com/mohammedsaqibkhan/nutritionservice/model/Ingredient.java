package com.mohammedsaqibkhan.nutritionservice.model;


import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private double quantity;
    private String unit;
}

