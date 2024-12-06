package com.mohammedsaqibkhan.nutritionservice.processor;

import com.mohammedsaqibkhan.nutritionservice.dto.NutritionixResponseDTO;
import com.mohammedsaqibkhan.nutritionservice.mapper.NutrientInfo;
import com.mohammedsaqibkhan.nutritionservice.mapper.NutrientMapper;



public class NutritionixResponseProcessor {




    public NutritionixResponseDTO processResponse(NutritionixResponseDTO responseDTO) {
        for (NutritionixResponseDTO.FoodDTO food : responseDTO.getFoods()) {
            for (NutritionixResponseDTO.FullNutrientDTO fullNutrient : food.getFull_nutrients()) {
                NutrientInfo nutrientInfo = NutrientMapper.getNutrientInfo(fullNutrient.getAttr_id());
                fullNutrient.setNutrient_name(nutrientInfo.getName());
                fullNutrient.setCategory(nutrientInfo.getCategory());
                fullNutrient.setUnit(nutrientInfo.getUnit());
            }
        }
        return responseDTO;
    }

}

