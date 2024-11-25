package com.mohammedsaqibkhan.recipeservice.mapper;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeIngredientDTO;
import com.mohammedsaqibkhan.recipeservice.entity.RecipeIngredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient);

    RecipeIngredient toEntity(RecipeIngredientDTO ingredientDTO);
}
