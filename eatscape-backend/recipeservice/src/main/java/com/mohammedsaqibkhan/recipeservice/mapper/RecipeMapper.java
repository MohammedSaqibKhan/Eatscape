package com.mohammedsaqibkhan.recipeservice.mapper;

import com.mohammedsaqibkhan.recipeservice.dto.DietTypeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.MealTypeDTO;
import com.mohammedsaqibkhan.recipeservice.dto.RecipeDTO;
import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import com.mohammedsaqibkhan.recipeservice.entity.MealType;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO toDTO(Recipe recipe);

    @Mapping(target = "difficultyLevel", source = "difficultyLevel")
    Recipe toEntity(RecipeDTO recipeDTO);

    // Nested mappings
    DietTypeDTO dietTypeToDietTypeDTO(DietType dietType);
    DietType dietTypeDTOToDietType(DietTypeDTO dietTypeDTO);


    MealTypeDTO mealTypeToMealTypeDTO(MealType mealType);
    MealType mealTypeDTOToMealType(MealTypeDTO mealTypeDTO);

}
