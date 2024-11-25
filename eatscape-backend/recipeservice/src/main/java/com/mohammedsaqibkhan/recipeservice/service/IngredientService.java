package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.RecipeIngredient;
import java.util.List;

public interface IngredientService {
    List<RecipeIngredient> getAllIngredients();
}