package com.mohammedsaqibkhan.mealservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AltMeasure {

    @JsonProperty("serving_weight")
    private double servingWeight;

    private String measure;

    private int seq;

    private int qty;

    // Getters and setters
}

