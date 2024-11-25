package com.mohammedsaqibkhan.recipeservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealTypeDTO {
    private Long id;
    private String name;
    @JsonIgnore
    private List<RecipeDTO> recipes;
}
