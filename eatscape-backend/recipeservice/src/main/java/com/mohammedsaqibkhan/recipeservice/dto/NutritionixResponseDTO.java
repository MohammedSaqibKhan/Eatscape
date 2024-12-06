package com.mohammedsaqibkhan.recipeservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NutritionixResponseDTO {

    private List<FoodDTO> foods;

    @Setter
    @Getter
    public static class FoodDTO {
        private String food_name;
        private String brand_name;
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
        private double nf_p;
        private List<FullNutrientDTO> full_nutrients;
        private String consumed_at;
        private MetadataDTO metadata;
        private String source;
        private String ndb_no;
        private TagsDTO tags;
        private List<AltMeasureDTO> alt_measures;
        private PhotoDTO photo;
    }

    @Setter
    @Getter
    @Data
    public static class FullNutrientDTO {
        private int attr_id;
        private String nutrient_name; // Replace attr_id with nutrient_name
        private double value;
        private String category;
        private String unit;
    }

    @Setter
    @Getter
    public static class MetadataDTO {
        private boolean is_raw_food;
    }

    @Setter
    @Getter
    public static class TagsDTO {
        private String item;
        private String measure;
        private String quantity;
        private String food_group;
        private String tag_id;
    }

    @Setter
    @Getter
    public static class AltMeasureDTO {
        private double serving_weight;
        private String measure;
        private int seq;
        private double qty;
    }

    @Setter
    @Getter
    public static class PhotoDTO {
        private String thumb;
        private String highres;
        private boolean is_user_uploaded;
    }
}
