package com.mohammedsaqibkhan.mealplanservice.repository;

import com.mohammedsaqibkhan.mealplanservice.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    Optional<Planner> findByDate(LocalDate date);
    void deleteByDate(LocalDate date);
}
