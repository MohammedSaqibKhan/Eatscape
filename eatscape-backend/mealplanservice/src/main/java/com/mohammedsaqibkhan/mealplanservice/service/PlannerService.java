package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.dto.FullNutrientDTO;
import com.mohammedsaqibkhan.mealplanservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.mealplanservice.entity.Planner;
import com.mohammedsaqibkhan.mealplanservice.repository.PlannerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlannerService {

    private WebClient webClient;
    private final PlannerRepository plannerRepository;

    @Value("${recipe.service.url}")
    private String recipeServiceUrl;

    // @PostConstruct ensures the @Value is injected before usage
    @PostConstruct
    public void init() {
        // Log the injected value here
        System.out.println("Recipe Service URL after injection: " + recipeServiceUrl);
        this.webClient = WebClient.builder().baseUrl(recipeServiceUrl).build();
    }

    // Constructor (no need to print the recipeServiceUrl here anymore)
    public PlannerService(WebClient.Builder webClientBuilder, PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
        // WebClient initialization is handled in the @PostConstruct method
    }

    // Fetch recipes by meal type from Recipe Service
    public List<RecipeDTO> getRecipesByMealType(String mealType) {
        return webClient.get()
                .uri("/recipes/meal-type?mealType={mealType}", mealType)
                .retrieve()
                .bodyToFlux(RecipeDTO.class)
                .collectList()
                .block();
    }

    // Generate a daily meal plan
    public Map<String, Object> generateDailyMealPlan(LocalDate date) {
        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
        Map<String, RecipeDTO> dailyMealPlan = new HashMap<>();
        Random random = new Random();

        for (String mealType : mealTypes) {
            List<RecipeDTO> recipes = getRecipesByMealType(mealType);
            if (!recipes.isEmpty()) {
                RecipeDTO randomRecipe = recipes.get(random.nextInt(recipes.size()));
                dailyMealPlan.put(mealType, randomRecipe);
            }
        }
        saveMealPlan(date, dailyMealPlan);
        return aggregateMealPlanWithNutrients(dailyMealPlan);
    }

    // Save a meal plan to the database
    @Transactional
    public void saveMealPlan(LocalDate date, Map<String, RecipeDTO> mealPlan) {
        // Map MealType to RecipeId
        Map<String, Long> mealPlanData = mealPlan.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getId()));

        // Check if a meal plan exists for the date
        Optional<Planner> existingPlan = plannerRepository.findByDate(date);

        if (existingPlan.isPresent()) {
            // Delete the existing meal plan
            plannerRepository.deleteByDate(date);
        }

        // Save the new meal plan
        Planner newMealPlan = Planner.builder()
                .date(date)
                .mealPlan(mealPlanData)
                .build();

        plannerRepository.save(newMealPlan);
    }

    // Retrieve a meal plan for a specific date
    public Map<String, Object> getMealPlan(LocalDate date) {
        // Fetch the meal plan from the repository
        Optional<Planner> plannerOptional = plannerRepository.findByDate(date);

        // If the planner does not exist, return an empty map
        if (plannerOptional.isEmpty()) {
            return Collections.emptyMap(); // Return an empty map instead of throwing an exception
        }

        Planner planner = plannerOptional.get();

        // Process each meal plan entry to fetch corresponding recipe details
        Map<String, RecipeDTO> mealPlan =  planner.getMealPlan().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            try {
                                // Fetch recipe by ID from RecipeService
                                return webClient.get()
                                        .uri("/recipes/{id}", entry.getValue())
                                        .retrieve()
                                        .bodyToMono(RecipeDTO.class)
                                        .block(); // block() to wait for the response synchronously
                            } catch (Exception e) {
                                // Handle WebClient errors and log them
                                throw new RuntimeException("Error fetching recipe for meal type: " + entry.getKey(), e);
                            }
                        }
                ));
        return aggregateMealPlanWithNutrients(mealPlan);
    }


    // Refresh the meal plan for a specific date
    @Transactional
    public Map<String, Object> refreshMealPlan(LocalDate date) {
        // Check if a meal plan exists for the date
        Optional<Planner> existingPlan = plannerRepository.findByDate(date);

        if (existingPlan.isPresent()) {
            // Delete the existing meal plan
            plannerRepository.deleteByDate(date);
        }

        // Generate a new daily meal plan
        List<String> mealTypes = Arrays.asList("Breakfast", "Lunch", "Dinner");
        Map<String, RecipeDTO> refreshMealPlan = new HashMap<>();
        Random random = new Random();

        for (String mealType : mealTypes) {
            List<RecipeDTO> recipes = getRecipesByMealType(mealType);
            if (!recipes.isEmpty()) {
                RecipeDTO randomRecipe = recipes.get(random.nextInt(recipes.size()));
                refreshMealPlan.put(mealType, randomRecipe);
            }
        }

        // Save the newly generated meal plan
        saveMealPlan(date, refreshMealPlan);

        return aggregateMealPlanWithNutrients(refreshMealPlan);

    }

    public Map<String, Object> aggregateMealPlanWithNutrients(Map<String, RecipeDTO> newMealPlan) {
        // Separate maps for main and full nutrients
        Map<String, Object> response = new HashMap<>();
        Map<String, Double> mainNutrients = new HashMap<>();
        List<Map<String, Object>> fullNutrients = new ArrayList<>();

        for (Map.Entry<String, RecipeDTO> entry : newMealPlan.entrySet()) {
            RecipeDTO recipe = entry.getValue();

            // Aggregate main nutrients
            addToTotal(mainNutrients, "Calories", recipe.getNutritionalInfo().getCalories());
            addToTotal(mainNutrients, "Protein", recipe.getNutritionalInfo().getProtein());
            addToTotal(mainNutrients, "Fat", recipe.getNutritionalInfo().getTotalFat());
            addToTotal(mainNutrients, "Carbs", recipe.getNutritionalInfo().getTotalCarbohydrates());
            addToTotal(mainNutrients, "Sugars", recipe.getNutritionalInfo().getSugars());
            addToTotal(mainNutrients, "Sodium", recipe.getNutritionalInfo().getSodium());
            addToTotal(mainNutrients, "Fiber", recipe.getNutritionalInfo().getDietaryFiber());
            addToTotal(mainNutrients, "Phosphorous", recipe.getNutritionalInfo().getPhosphorus());
            addToTotal(mainNutrients, "Saturated Fat", recipe.getNutritionalInfo().getSaturatedFat());
            addToTotal(mainNutrients, "Potassium", recipe.getNutritionalInfo().getPotassium());
            addToTotal(mainNutrients, "Cholesterol", recipe.getNutritionalInfo().getCholesterol());

            // Aggregate full nutrients from all ingredients
            if (recipe.getNutritionalInfo().getFullNutrients() != null) {
                for (FullNutrientDTO nutrient : recipe.getNutritionalInfo().getFullNutrients()) {
                    addFullNutrient(fullNutrients, nutrient);
                }
            }
        }

        response.put("mealPlan", newMealPlan);
        response.put("mainNutrients", mainNutrients);
        response.put("fullNutrients", fullNutrients);

        return response;
    }


    // Helper method to add nutrient values to the total
    private void addToTotal(Map<String, Double> nutrients, String nutrientName, Double value) {
        nutrients.put(nutrientName, nutrients.getOrDefault(nutrientName, 0.0) + (value != null ? value : 0.0));
    }

    // Helper method to add full nutrient details
    private void addFullNutrient(List<Map<String, Object>> fullNutrients, FullNutrientDTO nutrient) {
        String name = nutrient.getNutrientName();
        double value = nutrient.getValue() != 0 ? nutrient.getValue() : 0.0;
        String category = nutrient.getCategory();
        String unit = nutrient.getUnit();

        // Check if the nutrient is already in the list
        Map<String, Object> existingNutrient = fullNutrients.stream()
                .filter(n -> n.get("nutrientName").equals(name))
                .findFirst()
                .orElse(null);

        if (existingNutrient != null) {
            // Update the existing nutrient value
            double currentValue = (double) existingNutrient.get("value");
            existingNutrient.put("value", currentValue + value);
        } else {
            // Add a new nutrient entry
            Map<String, Object> nutrientDetails = new HashMap<>();
            nutrientDetails.put("nutrientName", name);
            nutrientDetails.put("category", category);
            nutrientDetails.put("unit", unit);
            nutrientDetails.put("value", value);

            fullNutrients.add(nutrientDetails);
        }
    }
}
