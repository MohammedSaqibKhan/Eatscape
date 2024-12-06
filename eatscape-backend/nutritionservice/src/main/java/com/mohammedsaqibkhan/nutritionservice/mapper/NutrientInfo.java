package com.mohammedsaqibkhan.nutritionservice.mapper;

import lombok.Data;

// Class to hold the nutrient's information
@Data
public class NutrientInfo {
    private String name;
    private String unit;
    private String category;

    public NutrientInfo(String name, String unit, String category) {
        this.name = name;
        this.unit = unit;
        this.category = category;
    }

}
