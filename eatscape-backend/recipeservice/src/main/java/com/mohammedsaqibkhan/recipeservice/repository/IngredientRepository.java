package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // Correct query methods based on the actual fields in RecipeIngredient
    List<RecipeIngredient> findByIngredientNameContaining(String name);

    List<RecipeIngredient> findByCategoryContaining(String category);
}