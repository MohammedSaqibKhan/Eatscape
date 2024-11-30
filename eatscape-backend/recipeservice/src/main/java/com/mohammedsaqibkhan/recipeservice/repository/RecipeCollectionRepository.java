package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.RecipeCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long> {
    List<RecipeCollection> findByIsDeletedFalse();
}
