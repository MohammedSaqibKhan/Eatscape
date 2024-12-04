package com.mohammedsaqibkhan.nutritionservice.processor;

import com.mohammedsaqibkhan.nutritionservice.dto.NutritionixResponseDTO;
import com.mohammedsaqibkhan.nutritionservice.mapper.NutrientMapper;

import java.util.stream.Collectors;



public class NutritionixResponseProcessor {


    public static NutritionixResponseDTO processResponse(NutritionixResponseDTO response) {
        if (response.getFoods() != null) {
            response.getFoods().forEach(food -> {
                if (food.getFull_nutrients() != null) {
                    food.setFull_nutrients(
                            food.getFull_nutrients().stream()
                                    .peek(nutrient -> nutrient.setNutrient_name(NutrientMapper.getNutrientName(nutrient.getAttr_id())))
                                    .collect(Collectors.toList())
                    );

                }
            });
        }
        return response;
    }
}

