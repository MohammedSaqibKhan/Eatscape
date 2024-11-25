package com.mohammedsaqibkhan.mealplanservice.controller;

import com.mohammedsaqibkhan.mealplanservice.dto.MealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/random")
public class MealPlannerController {

    private final WebClient webClient;

    @Autowired
    public MealPlannerController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build(); // Replace with Meal Service base URL
    }

    @GetMapping("/meals")
    public List<MealDto> fetchMealsByDietType(
            @RequestParam(required = false) String dietType,
            @RequestParam(required = false) Integer numMeals,
            @RequestParam(required = false) Integer protein,
            @RequestParam(required = false) Integer carbs,
            @RequestParam(required = false) Integer fat) {

        // Fetch all meals from the backend service
        List<MealDto> allMeals = webClient.get()
                .uri("/meals")  // Fetch all meals
                .retrieve()
                .bodyToFlux(MealDto.class)
                .collectList()
                .block();

        // Filter meals by diet type if provided
        if (dietType != null && !dietType.isEmpty()) {
            allMeals = allMeals.stream()
                    .filter(meal -> meal.getDietType().equalsIgnoreCase(dietType))
                    .collect(Collectors.toList());
        }

        // Closeness thresholds for each macronutrient
        int acceptableProteinRange = 10; // Acceptable range for protein (±10g)
        int acceptableCarbsRange = 10;   // Acceptable range for carbs (±10g)
        int acceptableFatRange = 5;      // Acceptable range for fat (±5g)

        allMeals = allMeals.stream()
                .filter(meal -> {
                    boolean isProteinClose = protein == null || Math.abs(meal.getProtein() - protein) <= acceptableProteinRange;
                    boolean isCarbsClose = carbs == null || Math.abs(meal.getCarbs() - carbs) <= acceptableCarbsRange;
                    boolean isFatClose = fat == null || Math.abs(meal.getFat() - fat) <= acceptableFatRange;

                    // Only include the meal if it is close to all provided target values
                    return isProteinClose && isCarbsClose && isFatClose;
                })
                .collect(Collectors.toList());

        // Shuffle the meals to ensure random selection every time
        Collections.shuffle(allMeals, new Random());



        // Limit the number of meals if numMeals is provided
        if (numMeals != null) {
            allMeals = allMeals.stream()
                    .limit(numMeals) // Limit meals based on the requested count
                    .collect(Collectors.toList());
        }

        return allMeals;
    }
}
