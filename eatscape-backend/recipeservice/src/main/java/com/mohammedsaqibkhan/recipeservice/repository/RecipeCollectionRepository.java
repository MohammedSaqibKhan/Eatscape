package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.RecipeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long> {
    List<RecipeCollection> findByIsDeletedFalse();

    @Query("SELECT c FROM RecipeCollection c WHERE c.isDeleted = false")
    List<RecipeCollection> findAllActiveCollections();
}
