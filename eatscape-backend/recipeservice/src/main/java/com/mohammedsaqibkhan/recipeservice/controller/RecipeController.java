package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.dto.MealPlanRequestDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeStatsDTO;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.RecipeRepository;
import com.mohammedsaqibkhan.recipeservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @GetMapping("/advance-dynamic-search")
    public ResponseEntity<Page<Recipe>> searchRecipesWithFilters(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> mealTypes,
            @RequestParam(required = false) List<String> dietTypes,
            @RequestParam(required = false) Integer minCalories,
            @RequestParam(required = false) Integer maxCalories,
            @RequestParam(required = false) Integer minCarbs,
            @RequestParam(required = false) Integer maxCarbs,
            @RequestParam(required = false) Integer minProtein,
            @RequestParam(required = false) Integer maxProtein,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size ) {

        Page<Recipe> recipes = recipeService.searchRecipesWithFilters(
                query, mealTypes, dietTypes, minCalories, maxCalories, minCarbs,
                maxCarbs, minProtein,
                maxProtein, page, size);

        return new ResponseEntity<>(recipes, HttpStatus.OK);
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

    @PostMapping("/daily-meal-plan")
    public ResponseEntity<Map<String, Recipe>> generateDailyMealPlans(@RequestParam("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        Map<String, Recipe> mealPlan = recipeService.generateDailyMealPlan(date);
        return ResponseEntity.ok(mealPlan);
    }


    @PostMapping("/daily-meal-plan/generate")
    public ResponseEntity<Map<String, Recipe>> generateDailyMealPlan(@RequestBody MealPlanRequestDTO request) {
        try {
            LocalDate date = LocalDate.parse(request.getDate());
            // Generate meal plan and save to DB in one go
            Map<String, Recipe> mealPlan = recipeService.generateDailyMealPlan(date);

            if (mealPlan == null || mealPlan.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            return ResponseEntity.ok(mealPlan); // Return the generated meal plan
        } catch (Exception e) {
            System.out.println("Error generating daily meal plan: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/daily-meal-plan/save")
    public ResponseEntity<String> saveDailyMealPlan(@RequestBody MealPlanRequestDTO request) {
        try {
            LocalDate date = LocalDate.parse(request.getDate());
            Map<String, Recipe> mealPlan = request.getMealPlan();

            // Clear any existing meal plan for the date
            recipeRepository.deleteByDate(date);

            // Save the meal plan to the database
            for (String mealType : mealPlan.keySet()) {
                Recipe recipe = mealPlan.get(mealType);
                recipe.setDate(date);
                recipeRepository.save(recipe); // Save the recipe for the given date
            }

            return ResponseEntity.ok("Meal plan saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving meal plan.");
        }
    }


    @PostMapping("/daily-meal-plan/refresh")
    public ResponseEntity<String> refreshDailyMealPlan(@RequestParam("date") String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);

            // Delete existing meal plan for the specified date
            recipeRepository.deleteByDate(date);

            // Generate the new meal plan for the date
            Map<String, Recipe> refreshedMealPlan = recipeService.generateDailyMealPlan(date);

            // Save the refreshed meal plan to the database
            for (String mealType : refreshedMealPlan.keySet()) {
                Recipe recipe = refreshedMealPlan.get(mealType);
                recipe.setDate(date);
                recipeRepository.save(recipe);
            }

            return ResponseEntity.ok("Meal plan refreshed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error refreshing meal plan.");
        }
    }

    @GetMapping("/daily-meal-plan")
    public ResponseEntity<Map<String, Recipe>> getDailyMealPlan(@RequestParam("date") String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);

                    List<Recipe> recipes = recipeRepository.findByDate(date);

            // Convert List<Recipe> to Map<String, Recipe> based on meal type
            Map<String, Recipe> mealPlan = recipes.stream()
                    .collect(Collectors.toMap(recipe -> recipe.getMealType().getName(), recipe -> recipe));recipeRepository.findByDate(date);


            if (mealPlan.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // No meal plan for that date
            }

            return ResponseEntity.ok(mealPlan); // Return the meal plan for the date
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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


    @GetMapping("/meal-type")
    public ResponseEntity<List<Recipe>> getRecipesByMealType(@RequestParam String mealType) {
        List<Recipe> recipes = recipeRepository.findByMealType(mealType);
        return ResponseEntity.ok(recipes);
    }


}