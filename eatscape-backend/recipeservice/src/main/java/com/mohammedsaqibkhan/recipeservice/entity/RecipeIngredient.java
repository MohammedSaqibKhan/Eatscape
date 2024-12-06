package com.mohammedsaqibkhan.recipeservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Recipe recipe;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    private String ingredientName;
    private String category;
    private String quantity;
    private String unit;


    private String imageUrl;
}