package com.mohammedsaqibkhan.recipeservice.mapper;

import com.mohammedsaqibkhan.recipeservice.dto.MealTypeDTO;
import com.mohammedsaqibkhan.recipeservice.entity.MealType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealTypeMapper {

    MealTypeDTO toDTO(MealType mealType);

    MealType toEntity(MealTypeDTO mealTypeDTO);
}
