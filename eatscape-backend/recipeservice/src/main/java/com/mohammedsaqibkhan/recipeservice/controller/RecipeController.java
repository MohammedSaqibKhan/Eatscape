package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import com.mohammedsaqibkhan.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // Create a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe) {
        Recipe createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    // Get all recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/dynamic-search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String query) {
        List<Recipe> recipes = recipeService.searchRecipesByName(query);
        return ResponseEntity.ok(recipes);
    }


    // Get recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return recipe != null ? ResponseEntity.ok(recipe) : ResponseEntity.notFound().build();
    }

    // Update a recipe
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe != null ? ResponseEntity.ok(updatedRecipe) : ResponseEntity.notFound().build();
    }

    // Delete a recipe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Search recipes by name or tag
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tag) {
        List<Recipe> recipes = recipeService.searchRecipes(name, tag);
        return ResponseEntity.ok(recipes);
    }

    // Get popular recipes (e.g., top 10 by views)
    @GetMapping("/popular")
    public ResponseEntity<List<Recipe>> getPopularRecipes() {
        List<Recipe> popularRecipes = recipeService.getPopularRecipes();
        return ResponseEntity.ok(popularRecipes);
    }

    // Mark a recipe as favorite
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> markAsFavorite(@PathVariable Long id) {
        boolean marked = recipeService.markAsFavorite(id);
        return marked ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Recipe> findRecipeByName(@PathVariable String name) {

        Optional<Recipe> recipe = recipeService.getRecipeByName(name);
        return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
    }


    @GetMapping("/daily-random")
    public Map<String, Recipe> getDailyRandomRecipes() {
        return recipeService.getRandomRecipesForMeals();
    }

}