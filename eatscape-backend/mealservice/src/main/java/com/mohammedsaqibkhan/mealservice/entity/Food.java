package com.mohammedsaqibkhan.mealservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.boot.Metadata;

import java.util.List;
import java.util.Map;

@Data
public class Food {

    @JsonProperty("food_name")
    private String foodName;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("serving_qty")
    private int servingQty;

    @JsonProperty("serving_unit")
    private String servingUnit;

    @JsonProperty("serving_weight_grams")
    private double servingWeightGrams;

    @JsonProperty("nf_calories")
    private double nfCalories;

    @JsonProperty("nf_total_fat")
    private double nfTotalFat;

    @JsonProperty("nf_saturated_fat")
    private double nfSaturatedFat;

    @JsonProperty("nf_cholesterol")
    private double nfCholesterol;

    @JsonProperty("nf_sodium")
    private double nfSodium;

    @JsonProperty("nf_total_carbohydrate")
    private double nfTotalCarbohydrate;

    @JsonProperty("nf_dietary_fiber")
    private double nfDietaryFiber;

    @JsonProperty("nf_sugars")
    private Double nfSugars;

    @JsonProperty("nf_protein")
    private double nfProtein;

    @JsonProperty("nf_potassium")
    private double nfPotassium;

    @JsonProperty("nf_p")
    private double nfP;



    @JsonProperty("nix_brand_name")
    private String nixBrandName;

    @JsonProperty("nix_brand_id")
    private String nixBrandId;

    @JsonProperty("nix_item_name")
    private String nixItemName;

    @JsonProperty("nix_item_id")
    private String nixItemId;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("consumed_at")
    private String consumedAt;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("source")
    private int source;

    @JsonProperty("ndb_no")
    private int ndbNo;

    private Tags tags;

    @JsonProperty("alt_measures")
    private List<AltMeasure> altMeasures;

    private Photo photo;

    @JsonProperty("sub_recipe")
    private Object subRecipe;

    @JsonProperty("class_code")
    private Object classCode;

    @JsonProperty("brick_code")
    private Object brickCode;

    @JsonProperty("tag_id")
    private Object tagId;

    // Getters and setters
}

