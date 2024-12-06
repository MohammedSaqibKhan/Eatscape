package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

@Data
public class FullNutrientDTO {
    private Long id;
    private NutritionalInfoDTO nutritionalInfo;

    private int attrId;

    private String nutrientName;

    private double value;

    private String category;

    private String unit;
}


