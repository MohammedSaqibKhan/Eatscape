package com.mohammedsaqibkhan.mealservice.entity;


import java.util.List;

public class NutritionRes {
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "NutritionResponse{" +
                "foods=" + foods +
                '}';
    }
}
