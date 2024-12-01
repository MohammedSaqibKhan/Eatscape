package com.mohammedsaqibkhan.recipeservice.controller;

import com.mohammedsaqibkhan.recipeservice.dto.RecipeCollectionDTO;
import com.mohammedsaqibkhan.recipeservice.entity.RecipeCollection;
import com.mohammedsaqibkhan.recipeservice.service.RecipeCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class RecipeCollectionController {

    private final RecipeCollectionService recipeCollectionService;

    // Get all collections
    @GetMapping
    public ResponseEntity<List<RecipeCollection>> getAllCollections() {
        List<RecipeCollection> collections = recipeCollectionService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    // Create a new collection with optional recipes
    @PostMapping
    public ResponseEntity<RecipeCollection> createCollection(@RequestBody RecipeCollectionDTO collectionDTO) {
        RecipeCollection createdCollection = recipeCollectionService.createCollection(collectionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCollection);
    }

    // Get a specific collection by ID and its recipes
    @GetMapping("/{id}")
    public ResponseEntity<RecipeCollection> getCollectionById(@PathVariable Long id) {
        Optional<RecipeCollection> collection = recipeCollectionService.getCollectionById(id);
        return collection.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/{collectionId}/recipes/{recipeId}")
    public ResponseEntity<RecipeCollection> addRecipeToCollection(
            @PathVariable Long collectionId, @PathVariable Long recipeId) {
        RecipeCollection updatedCollection = recipeCollectionService.addRecipeToCollection(collectionId, recipeId);
        return ResponseEntity.ok(updatedCollection);
    }


    @DeleteMapping("/{collectionId}/recipes/{recipeId}")
    public ResponseEntity<RecipeCollection> removeRecipeFromCollection(
            @PathVariable Long collectionId, @PathVariable Long recipeId) {
        RecipeCollection updatedCollection = recipeCollectionService.removeRecipeFromCollection(collectionId, recipeId);
        return ResponseEntity.ok(updatedCollection);
    }


    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long collectionId) {
        recipeCollectionService.deleteCollectionById(collectionId);
        return ResponseEntity.noContent().build();
    }


}
