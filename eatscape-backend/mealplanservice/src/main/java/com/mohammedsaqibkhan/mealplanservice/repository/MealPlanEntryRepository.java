package com.mohammedsaqibkhan.mealplanservice.repository;

import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MealPlanEntryRepository extends JpaRepository<MealPlanEntry, Long> {

}