package com.mohammedsaqibkhan.mealservice.repository;

import com.mohammedsaqibkhan.mealservice.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByName(String name);
    // Additional query methods can be defined here
}
