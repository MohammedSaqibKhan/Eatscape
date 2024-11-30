package com.mohammedsaqibkhan.recipeservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class RecipeCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ElementCollection
    private List<String> tags; // Optional list of tags for the collection (e.g., Vegan, Low-Carb)

    @ManyToMany
    @JoinTable(
            name = "collection_recipe",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes; // A collection can have multiple recipes

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
