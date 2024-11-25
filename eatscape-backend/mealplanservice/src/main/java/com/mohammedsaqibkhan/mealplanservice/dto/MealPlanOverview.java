package com.mohammedsaqibkhan.mealplanservice.dto;

import lombok.Data;

@Data
public class MealPlanOverview {
    private String title;
    private String value;
    private String trend;

    public MealPlanOverview(String title, String value, String trend) {
        this.title = title;
        this.value = value;
        this.trend = trend;
    }
}
