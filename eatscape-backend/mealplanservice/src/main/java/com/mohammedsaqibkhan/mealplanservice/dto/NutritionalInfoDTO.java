package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class NutritionalInfoDTO {

    private Long id;

    private Long recipeId;

    private List<FullNutrientDTO> fullNutrients;

    private Double calories;

    private Double totalFat;

    private Double saturatedFat;

    private Double cholesterol;

    private Double sodium;

    private Double totalCarbohydrates;

    private Double dietaryFiber;

    private Double sugars;

    private Double protein;

    private Double potassium;

    private Double phosphorus;

    private String foodName;

    private String brandName;

    private Double servingQty;

    private String servingUnit;

    private Double servingWeightGrams;

    private String source;

    private Boolean isRawFood;

    private String ndbNo;


}
