package com.mohammedsaqibkhan.recipeservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "full_nutrient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullNutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nutritional_info_id", nullable = false)
    @JsonIgnore
    private NutritionalInfo nutritionalInfo;

    @Column(name = "attr_id")
    private int attrId;

    @Column(name = "nutrient_name")
    private String nutrientName;

    @Column(name = "value")
    private double value;

    @Column(name = "category")
    private String category;

    @Column(name = "unit")
    private String unit;
}

