package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeCollectionDTO;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import com.mohammedsaqibkhan.recipeservice.entity.RecipeCollection;
import com.mohammedsaqibkhan.recipeservice.repository.RecipeCollectionRepository;
import com.mohammedsaqibkhan.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeCollectionService {

    private final RecipeCollectionRepository recipeCollectionRepository;
    private final RecipeRepository recipeRepository;

    // Get all collections
    public List<RecipeCollection> getAllCollections() {
        return recipeCollectionRepository.findByIsDeletedFalse();
    }

    // Create a new collection with optional recipes
    public RecipeCollection createCollection(RecipeCollectionDTO collectionDTO) {
        RecipeCollection collection = new RecipeCollection();
        collection.setName(collectionDTO.getName());
        collection.setDescription(collectionDTO.getDescription());
        collection.setTags(collectionDTO.getTags());

        // Add recipes to the collection if recipeIds are provided
        if (collectionDTO.getRecipeIds() != null) {
            List<Recipe> recipes = recipeRepository.findAllById(collectionDTO.getRecipeIds());
            collection.setRecipes(recipes);
        }

        return recipeCollectionRepository.save(collection);
    }

    // Get a specific collection by ID
    public Optional<RecipeCollection> getCollectionById(Long id) {
        return recipeCollectionRepository.findById(id);
    }


    public RecipeCollection addRecipeToCollection(Long collectionId, Long recipeId) {
        RecipeCollection collection = recipeCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if(collection.getRecipes().stream().anyMatch(existingRecipe -> existingRecipe.getId().equals(recipeId)))
            throw new RuntimeException("Recipe already exists in the collection");

        collection.getRecipes().add(recipe);
        return recipeCollectionRepository.save(collection);
    }


    public RecipeCollection removeRecipeFromCollection(Long collectionId, Long recipeId) {
        RecipeCollection collection = recipeCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        collection.getRecipes().removeIf(recipe -> recipe.getId().equals(recipeId));
        return recipeCollectionRepository.save(collection);
    }

    public void deleteCollectionById(Long collectionId) {
        RecipeCollection collection = recipeCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        collection.markAsDeleted();
        recipeCollectionRepository.save(collection);
    }

}
