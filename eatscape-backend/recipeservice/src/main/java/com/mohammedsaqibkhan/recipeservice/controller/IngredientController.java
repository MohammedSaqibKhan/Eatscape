package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.entity.RecipeIngredient;
import com.mohammedsaqibkhan.recipeservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    // Get a list of common ingredients
    @GetMapping
    public ResponseEntity<List<RecipeIngredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }
}