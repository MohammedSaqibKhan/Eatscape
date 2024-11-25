package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.MealType;

import java.util.List;

public interface MealTypeService {
    MealType createMealType(MealType mealType);

    List<MealType> getAllMealTypes();

    MealType getMealTypeById(Long id);

    MealType updateMealType(Long id, MealType mealType);

    void deleteMealType(Long id);
}