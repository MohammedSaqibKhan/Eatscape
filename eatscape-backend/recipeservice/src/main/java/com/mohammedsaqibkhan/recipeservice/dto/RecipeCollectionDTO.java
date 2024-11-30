package com.mohammedsaqibkhan.recipeservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeCollectionDTO {
    private String name;
    private String description;
    private List<String> tags; // Optional list of tags
    private List<Long> recipeIds; // List of recipe IDs to add to the collection (optional)
}
