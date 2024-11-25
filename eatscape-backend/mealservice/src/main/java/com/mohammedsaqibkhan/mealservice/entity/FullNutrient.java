package com.mohammedsaqibkhan.mealservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FullNutrient {

    @JsonProperty("attr_id")
    private int attrId;

    @JsonProperty("value")
    private double value;

    // Getters and setters
}
