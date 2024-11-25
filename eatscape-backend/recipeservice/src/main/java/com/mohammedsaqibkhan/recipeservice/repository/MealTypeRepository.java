package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealTypeRepository extends JpaRepository<MealType, Long> {
    // Custom query to find meal types by name
    List<MealType> findByNameContaining(String name);
}