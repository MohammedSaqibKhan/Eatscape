package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeStatsDTO;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.RecipeRepository;
import com.mohammedsaqibkhan.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    // Create a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipe) {
        Recipe createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByIsDeletedFalse();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        return recipe;
    }

    @GetMapping("/dynamic-search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String query) {
        List<Recipe> recipes = recipeService.searchRecipesByName(query);
        return ResponseEntity.ok(recipes);
    }




    // Update a recipe
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        // Copy properties from updatedRecipe to recipe
        recipe.setName(updatedRecipe.getName());
        recipe.setDescription(updatedRecipe.getDescription());
        // Update other fields as needed
        return recipeRepository.save(recipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        recipe.setDeleted(true);
        recipeRepository.save(recipe);
        return ResponseEntity.ok("Recipe marked as deleted.");
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

//    // Mark a recipe as favorite
//    @PostMapping("/{id}/favorite")
//    public ResponseEntity<Void> markAsFavorite(@PathVariable Long id) {
//        boolean marked = recipeService.markAsFavorite(id);
//        return marked ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
//    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Recipe> findRecipeByName(@PathVariable String name) {

        Optional<Recipe> recipe = recipeService.getRecipeByName(name);
        return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
    }


    @GetMapping("/daily-random")
    public Map<String, Recipe> getDailyRandomRecipes() {
        return recipeService.getRandomRecipesForMeals();
    }


    @PostMapping("/{id}/rating")
    public ResponseEntity<?> addRating(@PathVariable Long id, @RequestBody double rating) {
        Recipe recipe = recipeService.findById(id);

        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }

        recipe.addRating(rating);

        recipeService.save(recipe); // Save the updated recipe

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("averageRating", recipe.getAverageRating());
        response.put("totalRatings", recipe.getTotalRatings());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/view")
    public ResponseEntity<?> markAsViewed(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        if(recipe == null) return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);

        recipe.setViews(recipe.getViews() + 1);
        recipeRepository.save(recipe);

        return new ResponseEntity<>(Map.of("views",recipe.getViews()), HttpStatus.OK);
    }


    @PostMapping("/{id}/favorite")
    public ResponseEntity<?> toggleFavorite(@PathVariable Long id, @RequestParam boolean isFavorite) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
        if (isFavorite) {
            recipe.setFavorites(recipe.getFavorites() + 1);
        } else {
            recipe.setFavorites(recipe.getFavorites() - 1);
        }
        recipeService.save(recipe); // Save updated recipe
        return ResponseEntity.ok(Map.of("favorites", recipe.getFavorites(), "isFavorite", isFavorite));
    }


    @GetMapping("/stats")
    public List<RecipeStatsDTO> getRecipeStats() {
        return recipeService.getRecipeStats();
    }


    @GetMapping("/category-distribution")
    public Map<String, Double> getCategoryDistribution() {
        return recipeService.getCategoryDistribution();
    }

}