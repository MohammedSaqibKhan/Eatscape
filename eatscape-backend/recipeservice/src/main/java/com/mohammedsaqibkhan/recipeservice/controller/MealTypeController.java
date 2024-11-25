package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.entity.MealType;
import com.mohammedsaqibkhan.recipeservice.service.MealTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meal-types")
@RequiredArgsConstructor
public class MealTypeController {

    private final MealTypeService mealTypeService;

    // Create a new meal type
    @PostMapping
    public ResponseEntity<MealType> createMealType(@RequestBody MealType mealType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealTypeService.createMealType(mealType));
    }

    // Get all meal types
    @GetMapping
    public ResponseEntity<List<MealType>> getAllMealTypes() {
        return ResponseEntity.ok(mealTypeService.getAllMealTypes());
    }

    // Get meal type by ID
    @GetMapping("/{id}")
    public ResponseEntity<MealType> getMealTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(mealTypeService.getMealTypeById(id));
    }

    // Update a meal type
    @PutMapping("/{id}")
    public ResponseEntity<MealType> updateMealType(@PathVariable Long id, @RequestBody MealType mealType) {
        return ResponseEntity.ok(mealTypeService.updateMealType(id, mealType));
    }

    // Delete a meal type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealType(@PathVariable Long id) {
        mealTypeService.deleteMealType(id);
        return ResponseEntity.noContent().build();
    }
}