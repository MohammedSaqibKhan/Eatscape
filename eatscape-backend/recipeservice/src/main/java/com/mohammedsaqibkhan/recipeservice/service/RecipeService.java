package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeStatsDTO;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;

import java.util.*;

public interface RecipeService {
    Recipe createRecipe(RecipeDTO recipe);

    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    Optional<Recipe> getRecipeByName(String name);

    Recipe updateRecipe(Long id, Recipe recipe);

    boolean deleteRecipe(Long id);

    List<Recipe> searchRecipes(String name, String tag);

    List<Recipe> getPopularRecipes();

    boolean markAsFavorite(Long id);

    List<Recipe> searchRecipesByName(String query);

    Map<String, Recipe> getRandomRecipesForMeals();

    Recipe findById(Long id);

    void save(Recipe recipe);

    List<RecipeStatsDTO> getRecipeStats();

    Map<String, Double> getCategoryDistribution();
}