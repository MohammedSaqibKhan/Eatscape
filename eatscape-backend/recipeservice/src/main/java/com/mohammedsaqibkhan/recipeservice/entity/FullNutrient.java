package com.mohammedsaqibkhan.recipeservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "full_nutrient")
@Getter
@Setter
@Data
@Builder
public class FullNutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nutritional_info_id", nullable = false)
    private NutritionalInfo nutritionalInfo;

    @Column(name = "attr_id")
    private int attrId;

    @Column(name = "nutrient_name")
    private String nutrientName;

    @Column(name = "value")
    private double value;
}

