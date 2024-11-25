package com.mohammedsaqibkhan.mealservice.controller;

import com.mohammedsaqibkhan.mealservice.entity.NutritionRes;
import com.mohammedsaqibkhan.mealservice.model.Meal;
import com.mohammedsaqibkhan.mealservice.model.NutritionResponse;
import com.mohammedsaqibkhan.mealservice.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{foodId}")
    public NutritionRes getMealById(@PathVariable Long foodId) {
        NutritionRes meal = mealService.getMealById(foodId);
        System.out.println(meal.toString());
        return meal;
    }


    @GetMapping("/meal-name/{name}")
    public Meal getMealByName(@PathVariable String name) {
        return mealService.getMealByName(name);
    }

    @PostMapping("/save-meal")
    public Meal createMeal(@RequestBody Meal meal) {
        return mealService.createMeal(meal);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Meal> updateMeal(@PathVariable String name, @RequestBody Meal mealDetails) {
        Meal updatedMeal = mealService.updateMeal(name, mealDetails);
        return updatedMeal != null ? ResponseEntity.ok(updatedMeal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete-meals")
    public ResponseEntity<Void> deleteMeals(@RequestBody List<Long> ids) {
        mealService.deleteMealsByIds(ids);
        return ResponseEntity.noContent().build();
    }

}
