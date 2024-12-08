package com.mohammedsaqibkhan.recipeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammedsaqibkhan.recipeservice.dto.NutritionixResponseDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeStatsDTO;
import com.mohammedsaqibkhan.recipeservice.entity.*;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final DietTypeRepository dietTypeRepository;
    private final MealTypeRepository mealTypeRepository;
    private final IngredientRepository ingredientRepository;
    private final NutritionalInfoRepository nutritionalInfoRepository;
    private final RecipeStepRepository recipeStepRepository;

    private final CraiyonClient craiyonClient;

    private final RestTemplate restTemplate;

    private final String nutritionendpoint = "http://localhost:8088/api/nutrition";

    private final ImageService imageService;

    @Override
    @Transactional
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

        // Handle image URL
        String imageUrl = handleImage(recipeRequest);

        // Build and save the Recipe entity
        Recipe recipe = Recipe.builder()
                .name(recipeRequest.getName())
                .description(recipeRequest.getDescription())
                .prepTime(recipeRequest.getPrepTime())
                .cookTime(recipeRequest.getCookTime())
                .servings(recipeRequest.getServings())
                .dietType(dietType)
                .mealType(mealType)
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

        Recipe savedRecipe = recipeRepository.save(recipe);

        // Save Steps
        saveSteps(recipeRequest, savedRecipe);

        NutritionixResponseDTO nutritionResponse = fetchNutritionFromService(recipeRequest.getIngredients());
        // Fetch and Save Nutritional Info
        saveNutritionalInfo(recipeRequest, savedRecipe, nutritionResponse);

        // Save Ingredients
        saveIngredients(recipeRequest, savedRecipe);

        return savedRecipe;
    }

    private String handleImage(RecipeDTO recipeRequest) {
        String imageUrl = recipeRequest.getImageUrl();
        String dynamicFileName = recipeRequest.getName() + ".jpeg";

        Resource resource = new ClassPathResource("static/images/recipes/" + dynamicFileName);
        try (InputStream inputStream = resource.getInputStream()) {
            imageUrl = "/images/recipes/" + dynamicFileName;
        } catch (IOException e) {
            System.out.println("Image not found locally. Proceeding with provided URL.");
        }
        return imageUrl;
    }



    private void saveSteps(RecipeDTO recipeRequest, Recipe savedRecipe) {
        if (recipeRequest.getSteps() != null) {
            recipeRequest.getSteps().forEach(step -> {
                step.setRecipe(savedRecipe);
                step.setRecipeId(savedRecipe.getId());
            });
            recipeStepRepository.saveAll(recipeRequest.getSteps());
        }
    }

    private void saveNutritionalInfo(RecipeDTO recipeRequest, Recipe savedRecipe,
                                     NutritionixResponseDTO nutritionResponse) {
        if (recipeRequest.getIngredients() != null) {
            // Fetch nutrition info from the Nutrition Service

            NutritionalInfo nutritionalInfo = mapNutritionixToEntity(nutritionResponse);
            nutritionalInfo.setRecipe(savedRecipe);
            nutritionalInfo.setRecipeId(savedRecipe.getId());
            nutritionalInfoRepository.save(nutritionalInfo);
        }
    }



    // Method to save ingredients and associate them with the recipe
    public void saveIngredients(RecipeDTO recipeRequest, Recipe savedRecipe) {
        if (recipeRequest.getIngredients() != null) {
            List<RecipeIngredient> ingredients = recipeRequest.getIngredients();

            // For each ingredient, set the image URL from Pexels
            ingredients.forEach(ingredient -> {
                ingredient.setRecipe(savedRecipe);
                ingredient.setRecipeId(savedRecipe.getId());

                // Generate image for the ingredient using Pexels
                String ingredientName = ingredient.getIngredientName();
                String imageUrl = "";

                String dynamicFileName = ingredientName + ".jpeg";
                Resource resource = new ClassPathResource("static/images/recipes/" + dynamicFileName);
                try (InputStream inputStream = resource.getInputStream()) {
                    imageUrl = "/images/recipes/" + dynamicFileName;
                } catch (IOException e) {
                    System.out.println("Image not found locally. Proceeding with provided URL.");
                }

                // Set the image URL for the ingredient
                ingredient.setImageUrl(imageUrl);
            });

            // Save all the ingredients to the repository
            ingredientRepository.saveAll(ingredients);
        }
    }

    private NutritionixResponseDTO fetchNutritionFromService(List<RecipeIngredient> ingredients) {
        try {
            ResponseEntity<NutritionixResponseDTO> response = restTemplate.postForEntity(
                    nutritionendpoint + "/analyze", ingredients, NutritionixResponseDTO.class
            );
            log.info("Nutritionix API response: {}", response);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch nutrition data: " + e.getMessage());
        }
    }

    private NutritionalInfo mapNutritionixToEntity(NutritionixResponseDTO nutritionResponse) {
        NutritionalInfo nutritionalInfo = new NutritionalInfo();
        ObjectMapper objectMapper = new ObjectMapper();

        // Calculate total calories and other aggregated fields
        double totalCalories = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_calories).sum();
        nutritionalInfo.setCalories(totalCalories);

        double totalFat = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_total_fat).sum();
        nutritionalInfo.setTotalFat(totalFat);

        double saturatedFat = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_saturated_fat).sum();
        nutritionalInfo.setSaturatedFat(saturatedFat);

        double cholesterol = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_cholesterol).sum();
        nutritionalInfo.setCholesterol(cholesterol);

        double sodium = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_sodium).sum();
        nutritionalInfo.setSodium(sodium);

        double totalCarbohydrates = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_total_carbohydrate).sum();
        nutritionalInfo.setTotalCarbohydrates(totalCarbohydrates);

        double dietaryFiber = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_dietary_fiber).sum();
        nutritionalInfo.setDietaryFiber(dietaryFiber);

        double sugars = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_sugars).sum();
        nutritionalInfo.setSugars(sugars);

        double protein = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_protein).sum();
        nutritionalInfo.setProtein(protein);

        double potassium = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_potassium).sum();
        nutritionalInfo.setPotassium(potassium);

        double phosphorus = nutritionResponse.getFoods()
                .stream().mapToDouble(NutritionixResponseDTO.FoodDTO::getNf_p).sum();
        nutritionalInfo.setPhosphorus(phosphorus);

        // Aggregate nutrients
        List<Map<String, Object>> aggregatedFullNutrients = aggregateFullNutrients(nutritionResponse);

        // Map full nutrients
        List<FullNutrient> fullNutrientEntities = aggregatedFullNutrients.stream()
                .map(nutrientMap -> FullNutrient.builder()
                        .attrId((int) nutrientMap.get("attr_id"))
                        .nutrientName((String) nutrientMap.get("nutrient_name"))
                        .value((double) nutrientMap.get("value"))
                        .category((String) nutrientMap.get("category"))
                        .unit((String) nutrientMap.get("unit"))
                        .nutritionalInfo(nutritionalInfo) // Set parent entity
                        .build())
                .toList();

        nutritionalInfo.setFullNutrients(fullNutrientEntities); // Set mapped full nutrients


        // Assign non-aggregated fields from the first food item
        if (!nutritionResponse.getFoods().isEmpty()) {
            NutritionixResponseDTO.FoodDTO firstFood = nutritionResponse.getFoods().get(0);
            nutritionalInfo.setFoodName(firstFood.getFood_name());
            nutritionalInfo.setBrandName(firstFood.getBrand_name());
            nutritionalInfo.setServingQty(firstFood.getServing_qty());
            nutritionalInfo.setServingUnit(firstFood.getServing_unit());
            nutritionalInfo.setServingWeightGrams(firstFood.getServing_weight_grams());
            nutritionalInfo.setSource(firstFood.getSource());
            nutritionalInfo.setIsRawFood(firstFood.getMetadata().is_raw_food());
            nutritionalInfo.setNdbNo(firstFood.getNdb_no());
        }

        return nutritionalInfo;
    }

    // Helper method to aggregate full nutrients
    private List<Map<String, Object>> aggregateFullNutrients(NutritionixResponseDTO nutritionResponse) {
        // Map to store aggregated nutrient data
        Map<Integer, Map<String, Object>> aggregatedNutrients = new HashMap<>();

        // Iterate through foods and their full_nutrients
        nutritionResponse.getFoods().forEach(food -> {
            food.getFull_nutrients().forEach(nutrient -> {
                int attrId = nutrient.getAttr_id();
                aggregatedNutrients.putIfAbsent(attrId, new HashMap<>(Map.of(
                        "attr_id", attrId,
                        "nutrient_name", nutrient.getNutrient_name(),
                        "value", 0.0,
                        "category", nutrient.getCategory(),
                        "unit", nutrient.getUnit()
                )));

                // Increment the aggregated value
                Map<String, Object> nutrientData = aggregatedNutrients.get(attrId);
                nutrientData.put("value", (double) nutrientData.get("value") + nutrient.getValue());
            });
        });

        // Convert aggregated nutrient data into a list
        return new ArrayList<>(aggregatedNutrients.values());
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




}