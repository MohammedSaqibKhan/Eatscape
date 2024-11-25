package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import com.mohammedsaqibkhan.recipeservice.service.DietTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diet-types")
@RequiredArgsConstructor
public class DietTypeController {
    private final DietTypeService dietTypeService;

    // Create a new diet type
    @PostMapping
    public ResponseEntity<DietType> createDietType(@RequestBody DietType dietType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dietTypeService.createDietType(dietType));
    }

    // Get all diet types
    @GetMapping
    public ResponseEntity<List<DietType>> getAllDietTypes() {
        return ResponseEntity.ok(dietTypeService.getAllDietTypes());
    }

    // Get diet type by ID
    @GetMapping("/{id}")
    public ResponseEntity<DietType> getDietTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(dietTypeService.getDietTypeById(id));
    }

    // Update a diet type
    @PutMapping("/{id}")
    public ResponseEntity<DietType> updateDietType(@PathVariable Long id, @RequestBody DietType dietType) {
        return ResponseEntity.ok(dietTypeService.updateDietType(id, dietType));
    }

    // Delete a diet type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietType(@PathVariable Long id) {
        dietTypeService.deleteDietType(id);
        return ResponseEntity.noContent().build();
    }
}