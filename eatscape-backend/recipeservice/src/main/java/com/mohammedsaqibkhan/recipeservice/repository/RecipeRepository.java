package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Custom query to find recipes by name or tags
    List<Recipe> findByNameContaining(String name);

    List<Recipe> findByTagsContaining(String tag);

    // Custom query for name and tag search
    List<Recipe> findByNameContainingAndTagsContaining(String name, String tag);

    // Fetch top 10 popular recipes by views
    List<Recipe> findTop10ByOrderByViewsDesc();

    Optional<Recipe> findByNameAndDietType(String name, DietType dietType);


    // Additional custom queries can be added here

    Optional<Recipe> findByName(String name);

    List<Recipe> findByNameStartingWithIgnoreCase(String query);

    List<Recipe> findByMealType_NameIgnoreCase(String mealType);
}
