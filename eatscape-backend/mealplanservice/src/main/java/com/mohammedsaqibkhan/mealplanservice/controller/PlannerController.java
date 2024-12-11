package com.mohammedsaqibkhan.mealplanservice.controller;

import com.mohammedsaqibkhan.mealplanservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.mealplanservice.service.PlannerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/meal-plans")
public class PlannerController {
    private final PlannerService plannerService;

    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateMealPlan(@RequestParam String date) {
        LocalDate mealDate = LocalDate.parse(date);
        Map<String, Object> mealPlan = plannerService.generateDailyMealPlan(mealDate);
        return ResponseEntity.ok(mealPlan);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMealPlan(@RequestParam String date, @RequestBody Map<String, RecipeDTO> mealPlan) {
        LocalDate mealDate = LocalDate.parse(date);
        plannerService.saveMealPlan(mealDate, mealPlan);
        return ResponseEntity.ok("Meal plan saved successfully.");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMealPlan(@RequestParam String date) {
        LocalDate mealDate = LocalDate.parse(date);
        Map<String, Object> mealPlan = plannerService.getMealPlan(mealDate);
        if (mealPlan.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyMap());
        }
        return ResponseEntity.ok(mealPlan);
    }




    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshMealPlan(@RequestParam String date) {
        LocalDate mealDate = LocalDate.parse(date);
        Map<String, Object> refreshedMealPlan = plannerService.refreshMealPlan(mealDate);
        return ResponseEntity.ok(refreshedMealPlan);
    }



    @GetMapping("/recipes")
    public ResponseEntity<Page<RecipeDTO>> searchRecipes(
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
            @RequestParam(defaultValue = "20") int size) {

        Page<RecipeDTO> recipes = plannerService.searchRecipesWithFilters(
                query, mealTypes, dietTypes, minCalories, maxCalories,
                minCarbs, maxCarbs, minProtein, maxProtein, page, size);

        return ResponseEntity.ok(recipes);
    }
}



