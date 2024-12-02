package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeStatsDTO;
import com.mohammedsaqibkhan.recipeservice.entity.*;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final DietTypeRepository dietTypeRepository;
    private final MealTypeRepository mealTypeRepository;
    private final IngredientRepository ingredientRepository;
    private final NutritionalInfoRepository nutritionalInfoRepository;
    private final RecipeStepRepository recipeStepRepository;



    @Transactional
    @Override
    public Recipe createRecipe(RecipeDTO recipeRequest) {
        // Validate DietType: Check both ID and name
        System.out.println(recipeRequest.getDietType().getName() + "   " + recipeRequest.getDietType().getId());
        DietType dietType = dietTypeRepository.findById(recipeRequest.getDietType().getId())
                .filter(d -> d.getName().equals(recipeRequest.getDietType().getName()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid DietType ID or name: " + recipeRequest.getDietType().getId()));

        // Validate MealType: Check both ID and name
        MealType mealType = mealTypeRepository.findById(recipeRequest.getMealType().getId())
                .filter(m -> m.getName().equals(recipeRequest.getMealType().getName()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid MealType ID or name: " + recipeRequest.getMealType().getId()));

        // Check for duplicate recipe by name and dietType
        Optional<Recipe> existingRecipe = recipeRepository.findByNameAndDietType(recipeRequest.getName(), dietType);
        if (existingRecipe.isPresent()) {
            throw new IllegalArgumentException("Recipe with name '" + recipeRequest.getName() + "' and diet type '" + dietType.getName() + "' already exists.");
        }



        String imageUrl = recipeRequest.getImageUrl();
            String dynamicFileName = recipeRequest.getName().replaceAll("\\s+", "_") + ".jpeg";
            System.out.println("Dynamic file name: " + dynamicFileName);

            // Load the resource
            Resource resource = new ClassPathResource("static/images/recipes/" + dynamicFileName);

            try (InputStream inputStream = resource.getInputStream()) {
                // File exists
                imageUrl = "/images/recipes/" + dynamicFileName;
                System.out.println("Image found: " + imageUrl);
            } catch (IOException e) {
                // File does not exist
                System.out.println("Image not found locally. Proceeding to AI image generation...");
            }


        Recipe recipe = Recipe.builder()
                .name(recipeRequest.getName())
                .description(recipeRequest.getDescription())
                .prepTime(recipeRequest.getPrepTime())
                .cookTime(recipeRequest.getCookTime())
                .servings(recipeRequest.getServings())
                .dietType(recipeRequest.getDietType())
                .mealType(recipeRequest.getMealType())
                .imageUrl(imageUrl)
                .difficultyLevel(recipeRequest.getDifficultyLevel())
                .isFavorite(recipeRequest.isFavorite())
                .views(recipeRequest.getViews())
                .favorites(recipeRequest.getFavorites())
                .tags(recipeRequest.getTags())
                .cuisine(recipeRequest.getCuisine())
                .averageRating(recipeRequest.getAverageRating())
                .totalRatings(recipeRequest.getTotalRatings())
                .date(recipeRequest.getDate())
                .isDeleted(recipeRequest.isDeleted())
                .build();

        // Save the Recipe entity
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Set recipeId for ingredients and save
        if (recipeRequest.getIngredients() != null) {
            recipeRequest.getIngredients().forEach(ingredient -> {
                ingredient.setRecipe(savedRecipe); // Map recipe entity
                ingredient.setRecipeId(savedRecipe.getId()); // Set recipeId
            });
            List<RecipeIngredient> ingredients = ingredientRepository.saveAll(recipeRequest.getIngredients());
        }



        // Set recipeId for steps and save
        if (recipeRequest.getSteps() != null) {
            recipeRequest.getSteps().forEach(step -> {
                step.setRecipe(savedRecipe); // Map recipe entity
                step.setRecipeId(savedRecipe.getId()); // Set recipeId
            });
            recipeStepRepository.saveAll(recipeRequest.getSteps());
        }

        // Set recipeId for nutritionalInfo and save
        if (recipeRequest.getNutritionalInfo() != null) {
            NutritionalInfo nutritionalInfo = recipeRequest.getNutritionalInfo();
            nutritionalInfo.setRecipe(savedRecipe);
            nutritionalInfo.setRecipeId(savedRecipe.getId());
            nutritionalInfoRepository.save(nutritionalInfo);
        }

        return savedRecipe;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
    }

    @Override
    public Optional<Recipe> getRecipeByName(String name) {
        return Optional.ofNullable(recipeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with name: " + name)));
    }


    @Override
    public Recipe updateRecipe(Long id, Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        recipe.setId(existingRecipe.getId());
        return recipeRepository.save(recipe);
    }

    @Override
    public boolean deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        recipe.setDeleted(true);
        recipeRepository.save(recipe);
        return true;
    }

    @Override
    public List<Recipe> searchRecipes(String name, String tag) {
        if (name != null && tag != null) {
            return recipeRepository.findByNameContainingAndTagsContaining(name, tag);
        } else if (name != null) {
            return recipeRepository.findByNameContaining(name);
        } else if (tag != null) {
            return recipeRepository.findByTagsContaining(tag);
        }
        return List.of();
    }

    @Override
    public List<Recipe> getPopularRecipes() {
        return recipeRepository.findTop10ByOrderByViewsDesc();
    }

    @Override
    public boolean markAsFavorite(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with ID: " + id));
        recipe.setFavorites(recipe.getFavorites() + 1);
        recipeRepository.save(recipe);
        return true;
    }

    @Override
    public List<Recipe> searchRecipesByName(String query) {
        return recipeRepository.findByNameStartingWithIgnoreCaseAndIsDeletedFalse(query);
    }

    @Override
    public Map<String, Recipe> getRandomRecipesForMeals() {
        // Define meal types
        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
        Map<String, Recipe> randomRecipes = new HashMap<>();

        Random random = new Random();
        for (String mealType : mealTypes) {
            List<Recipe> filteredRecipes = recipeRepository.findByMealType_NameIgnoreCaseAndIsDeletedFalse(mealType);
            if (!filteredRecipes.isEmpty()) {
                Recipe randomRecipe = filteredRecipes.get(random.nextInt(filteredRecipes.size()));
                randomRecipes.put(mealType, randomRecipe);
            }
        }
        return randomRecipes;
    }




    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.get();
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeStatsDTO> getRecipeStats() {
        List<RecipeStatsDTO> recipeStatsDTOS = new ArrayList<>();

        long totalRecipes = recipeRepository.countByIsDeletedFalse();
        recipeStatsDTOS.add(new RecipeStatsDTO("Total Recipes", totalRecipes, "neutral", "Last 30 days"));


        recipeStatsDTOS.add(new RecipeStatsDTO("Vegan", recipeRepository.countByDietTypeIdAndIsDeletedFalse(1L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Gluten-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(2L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Low Carb", recipeRepository.countByDietTypeIdAndIsDeletedFalse(3L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Vegetarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(4L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Pescatarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(5L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Keto", recipeRepository.countByDietTypeIdAndIsDeletedFalse(6L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Paleo", recipeRepository.countByDietTypeIdAndIsDeletedFalse(7L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Dairy-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(8L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Nut-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(9L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Soy-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(10L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Mediterranean", recipeRepository.countByDietTypeIdAndIsDeletedFalse(11L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Whole30", recipeRepository.countByDietTypeIdAndIsDeletedFalse(12L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Halal", recipeRepository.countByDietTypeIdAndIsDeletedFalse(13L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Kosher", recipeRepository.countByDietTypeIdAndIsDeletedFalse(14L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Raw", recipeRepository.countByDietTypeIdAndIsDeletedFalse(15L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Diabetic-Friendly", recipeRepository.countByDietTypeIdAndIsDeletedFalse(16L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Low-Sodium", recipeRepository.countByDietTypeIdAndIsDeletedFalse(17L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Low-Fat", recipeRepository.countByDietTypeIdAndIsDeletedFalse(18L), "up", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("High-Protein", recipeRepository.countByDietTypeIdAndIsDeletedFalse(19L), "down", "Last 30 days"));
        recipeStatsDTOS.add(new RecipeStatsDTO("Non-Vegetarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(20L), "down", "Last 30 days"));

        return recipeStatsDTOS;
    }

    @Override
    public Map<String, Double> getCategoryDistribution() {
        Map<String, Long> distribution = new HashMap<>();
        long totalRecipes = recipeRepository.countByIsDeletedFalse();

        if (totalRecipes == 0) {
            // Avoid division by zero, return an empty map or 0% for all categories
            return distribution.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> 0.0));
        }

        distribution.put("Vegan", recipeRepository.countByDietTypeIdAndIsDeletedFalse(1L));
        distribution.put("Gluten-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(2L));
        distribution.put("Low Carb", recipeRepository.countByDietTypeIdAndIsDeletedFalse(3L));
        distribution.put("Vegetarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(4L));
        distribution.put("Pescatarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(5L));
        distribution.put("Keto", recipeRepository.countByDietTypeIdAndIsDeletedFalse(6L));
        distribution.put("Paleo", recipeRepository.countByDietTypeIdAndIsDeletedFalse(7L));
        distribution.put("Dairy-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(8L));
        distribution.put("Nut-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(9L));
        distribution.put("Soy-Free", recipeRepository.countByDietTypeIdAndIsDeletedFalse(10L));
        distribution.put("Mediterranean", recipeRepository.countByDietTypeIdAndIsDeletedFalse(11L));
        distribution.put("Whole30", recipeRepository.countByDietTypeIdAndIsDeletedFalse(12L));
        distribution.put("Halal", recipeRepository.countByDietTypeIdAndIsDeletedFalse(13L));
        distribution.put("Kosher", recipeRepository.countByDietTypeIdAndIsDeletedFalse(14L));
        distribution.put("Raw", recipeRepository.countByDietTypeIdAndIsDeletedFalse(15L));
        distribution.put("Diabetic-Friendly", recipeRepository.countByDietTypeIdAndIsDeletedFalse(16L));
        distribution.put("Low-Sodium", recipeRepository.countByDietTypeIdAndIsDeletedFalse(17L));
        distribution.put("Low-Fat", recipeRepository.countByDietTypeIdAndIsDeletedFalse(18L));
        distribution.put("High-Protein", recipeRepository.countByDietTypeIdAndIsDeletedFalse(19L));
        distribution.put("Non-Vegetarian", recipeRepository.countByDietTypeIdAndIsDeletedFalse(20L));

        // Convert counts to percentages
        return distribution.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / totalRecipes
                ));
    }

//    public Map<String, Recipe> getDailyMealPlanByDate(String date) {
//        // Define meal types
//        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
//        Map<String, Recipe> dailyMealPlan = new HashMap<>();
//
//        for (String mealType : mealTypes) {
//            // Fetch recipes for the specified meal type and date
//            List<Recipe> recipes = recipeRepository.findByMealType_NameAndDateAndIsDeletedFalse(mealType, date);
//
//            if (!recipes.isEmpty()) {
//                // Select the first recipe for simplicity, or implement your own logic
//                dailyMealPlan.put(mealType, recipes.get(0));
//            }
//        }
//
//        return dailyMealPlan;
//    }

    @Transactional
    public Map<String, Recipe> generateDailyMealPlan(LocalDate date) {
        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
        Map<String, Recipe> dailyMealPlan = new HashMap<>();

        Random random = new Random();

        for (String mealType : mealTypes) {
            List<Recipe> availableRecipes = recipeRepository.findByMealType(mealType);
            if (!availableRecipes.isEmpty()) {
                Recipe randomRecipe = availableRecipes.get(random.nextInt(availableRecipes.size()));
                randomRecipe.setDate(date); // Associate the recipe with the selected date
                recipeRepository.save(randomRecipe); // Persist the updated recipe
                dailyMealPlan.put(mealType, randomRecipe);
            }
        }

        return dailyMealPlan;
    }

    @Override
    public Map<String, Recipe> getMealPlanForDate(LocalDate date) {
            List<Recipe> recipesForDate = recipeRepository.findByDate(date);

            // Group recipes by meal type
            Map<String, Recipe> mealPlan = new HashMap<>();
            for (Recipe recipe : recipesForDate) {
                mealPlan.put(recipe.getMealType().getName(), recipe);
            }

            return mealPlan;
    }


}