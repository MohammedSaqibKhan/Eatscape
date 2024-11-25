package com.mohammedsaqibkhan.nutritionservice.model;

import lombok.Data;

import java.util.List;

@Data
public class NutritionResponse {

    private List<FoodItem> foods;

    @Data
    public static class FoodItem {
        private String food_name;
        private double serving_qty;
        private String serving_unit;
        private double serving_weight_grams;
        private double nf_calories;
        private double nf_total_fat;
        private double nf_saturated_fat;
        private double nf_cholesterol;
        private double nf_sodium;
        private double nf_total_carbohydrate;
        private double nf_dietary_fiber;
        private double nf_sugars;
        private double nf_protein;
        private double nf_potassium;
    }
}
