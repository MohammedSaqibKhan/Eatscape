package com.mohammedsaqibkhan.mealplanservice.repository;

import com.mohammedsaqibkhan.mealplanservice.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
//    Optional<MealPlan> findByUserId(Long userId);
    List<MealPlan> findByUserId(Long userId);


    @Query("SELECT COUNT(m) FROM MealPlan m WHERE m.userId = :userId")
    long countAllPlans(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM MealPlan m WHERE m.userId = :userId AND m.startDate <= CURRENT_DATE AND m.endDate >= CURRENT_DATE")
    long countActivePlans(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM MealPlan m WHERE m.userId = :userId AND m.startDate > CURRENT_DATE")
    long countUpcomingPlans(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM MealPlan m WHERE m.userId = :userId AND m.endDate < CURRENT_DATE")
    long countCompletedPlans(@Param("userId") Long userId);
}