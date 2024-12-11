package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    // Custom query to find recipes by name or tags
    List<Recipe> findByNameContaining(String name);

    List<Recipe> findByTagsContaining(String tag);

    // Custom query for name and tag search
    List<Recipe> findByNameContainingAndTagsContaining(String name, String tag);

    // Fetch top 10 popular recipes by views
    List<Recipe> findTop10ByOrderByViewsDesc();

    Optional<Recipe> findByNameAndDietType(String name, DietType dietType);


    // Additional custom queries can be added here

    Optional<Recipe> findByName(String name);

    List<Recipe> findByNameStartingWithIgnoreCaseAndIsDeletedFalse(String query);

    List<Recipe> findByMealType_NameIgnoreCaseAndIsDeletedFalse(String mealType);

    // Count all recipes that are not marked as deleted
    long countByIsDeletedFalse();

    // Count recipes by diet type and ensure they are not marked as deleted
    long countByDietTypeIdAndIsDeletedFalse(Long dietTypeId);

    List<Recipe> findAllByIsDeletedFalse();

    Optional<Recipe> findByIdAndIsDeletedFalse(Long id);

//    List<Recipe> findByMealType_NameAndDateAndIsDeletedFalse(String mealType, String date);


    // Fetch recipes for a specific meal type and date
    @Query("SELECT r FROM Recipe r WHERE r.mealType.name = :mealType AND r.date = :date AND r.isDeleted = false")
    List<Recipe> findByMealTypeAndDate(@Param("mealType") String mealType, @Param("date") LocalDate date);

    // Find recipes by date
    // Find recipes by date (return List instead of Map)
    List<Recipe> findByDate(LocalDate date);

    // Delete recipes by date
    void deleteByDate(LocalDate date);


    // Fetch all active recipes for a specific meal type
    @Query("SELECT r FROM Recipe r WHERE r.mealType.name = :mealType AND r.isDeleted = false")
    List<Recipe> findByMealType(@Param("mealType") String mealType);



}
