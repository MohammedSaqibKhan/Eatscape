package com.mohammedsaqibkhan.mealservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tags {

    private String item;

    private String measure;

    private String quantity;

    @JsonProperty("food_group")
    private int foodGroup;

    @JsonProperty("tag_id")
    private int tagId;

    // Getters and setters
}

