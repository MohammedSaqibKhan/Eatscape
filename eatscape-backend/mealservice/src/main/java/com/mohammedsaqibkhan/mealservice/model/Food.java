package com.mohammedsaqibkhan.mealservice.model;

public class Food {
    private String food_name;
    private int serving_qty;
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
    private double nf_p;

    // Getters and Setters

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getServing_qty() {
        return serving_qty;
    }

    public void setServing_qty(int serving_qty) {
        this.serving_qty = serving_qty;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public void setServing_unit(String serving_unit) {
        this.serving_unit = serving_unit;
    }

    public double getServing_weight_grams() {
        return serving_weight_grams;
    }

    public void setServing_weight_grams(double serving_weight_grams) {
        this.serving_weight_grams = serving_weight_grams;
    }

    public double getNf_calories() {
        return nf_calories;
    }

    public void setNf_calories(double nf_calories) {
        this.nf_calories = nf_calories;
    }

    public double getNf_total_fat() {
        return nf_total_fat;
    }

    public void setNf_total_fat(double nf_total_fat) {
        this.nf_total_fat = nf_total_fat;
    }

    public double getNf_saturated_fat() {
        return nf_saturated_fat;
    }

    public void setNf_saturated_fat(double nf_saturated_fat) {
        this.nf_saturated_fat = nf_saturated_fat;
    }

    public double getNf_cholesterol() {
        return nf_cholesterol;
    }

    public void setNf_cholesterol(double nf_cholesterol) {
        this.nf_cholesterol = nf_cholesterol;
    }

    public double getNf_sodium() {
        return nf_sodium;
    }

    public void setNf_sodium(double nf_sodium) {
        this.nf_sodium = nf_sodium;
    }

    public double getNf_total_carbohydrate() {
        return nf_total_carbohydrate;
    }

    public void setNf_total_carbohydrate(double nf_total_carbohydrate) {
        this.nf_total_carbohydrate = nf_total_carbohydrate;
    }

    public double getNf_dietary_fiber() {
        return nf_dietary_fiber;
    }

    public void setNf_dietary_fiber(double nf_dietary_fiber) {
        this.nf_dietary_fiber = nf_dietary_fiber;
    }

    public double getNf_sugars() {
        return nf_sugars;
    }

    public void setNf_sugars(double nf_sugars) {
        this.nf_sugars = nf_sugars;
    }

    @Override
    public String toString() {
        return "Food{" +
                "food_name='" + food_name + '\'' +
                ", serving_qty=" + serving_qty +
                ", serving_unit='" + serving_unit + '\'' +
                ", serving_weight_grams=" + serving_weight_grams +
                ", nf_calories=" + nf_calories +
                ", nf_total_fat=" + nf_total_fat +
                ", nf_saturated_fat=" + nf_saturated_fat +
                ", nf_cholesterol=" + nf_cholesterol +
                ", nf_sodium=" + nf_sodium +
                ", nf_total_carbohydrate=" + nf_total_carbohydrate +
                ", nf_dietary_fiber=" + nf_dietary_fiber +
                ", nf_sugars=" + nf_sugars +
                ", nf_protein=" + nf_protein +
                ", nf_potassium=" + nf_potassium +
                ", nf_p=" + nf_p +
                '}';
    }

    public double getNf_protein() {
        return nf_protein;
    }

    public void setNf_protein(double nf_protein) {
        this.nf_protein = nf_protein;
    }

    public double getNf_potassium() {
        return nf_potassium;
    }

    public void setNf_potassium(double nf_potassium) {
        this.nf_potassium = nf_potassium;
    }

    public double getNf_p() {
        return nf_p;
    }

    public void setNf_p(double nf_p) {
        this.nf_p = nf_p;
    }
}

