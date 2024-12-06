package com.mohammedsaqibkhan.recipeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Recipe recipe;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @OneToMany(mappedBy = "nutritionalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FullNutrient> fullNutrients;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "total_fat")
    private Double totalFat;

    @Column(name = "saturated_fat")
    private Double saturatedFat;

    @Column(name = "cholesterol")
    private Double cholesterol;

    @Column(name = "sodium")
    private Double sodium;

    @Column(name = "total_carbohydrates")
    private Double totalCarbohydrates;

    @Column(name = "dietary_fiber")
    private Double dietaryFiber;

    @Column(name = "sugars")
    private Double sugars;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "potassium")
    private Double potassium;

    @Column(name = "phosphorus")
    private Double phosphorus;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "serving_qty")
    private Double servingQty;

    @Column(name = "serving_unit")
    private String servingUnit;

    @Column(name = "serving_weight_grams")
    private Double servingWeightGrams;

    @Column(name = "source")
    private String source;

    @Column(name = "is_raw_food")
    private Boolean isRawFood;

    @Column(name = "ndb_no")
    private String ndbNo;


}
