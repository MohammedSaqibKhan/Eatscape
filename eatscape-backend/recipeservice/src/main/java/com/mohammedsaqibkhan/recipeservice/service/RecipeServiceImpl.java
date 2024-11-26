package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.entity.*;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
        if (imageUrl == null || imageUrl.isEmpty()) {
            String dynamicFileName = recipeRequest.getName().replaceAll("\\s+", "_") + ".jpg";
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
                .videoUrl(recipeRequest.getVideoUrl())
                .difficultyLevel(recipeRequest.getDifficultyLevel())
                .isFavorite(recipeRequest.isFavorite())
                .views(recipeRequest.getViews())
                .favorites(recipeRequest.getFavorites())
                .tags(recipeRequest.getTags())
                .cuisine(recipeRequest.getCuisine())
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
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found with ID: " + id);
        }
        recipeRepository.deleteById(id);
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
        return recipeRepository.findByNameStartingWithIgnoreCase(query);
    }

    @Override
    public Map<String, Recipe> getRandomRecipesForMeals() {
        // Define meal types
        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
        Map<String, Recipe> randomRecipes = new HashMap<>();

        Random random = new Random();
        for (String mealType : mealTypes) {
            List<Recipe> filteredRecipes = recipeRepository.findByMealType_NameIgnoreCase(mealType);
            if (!filteredRecipes.isEmpty()) {
                Recipe randomRecipe = filteredRecipes.get(random.nextInt(filteredRecipes.size()));
                randomRecipes.put(mealType, randomRecipe);
            }
        }
        return randomRecipes;
    }


}