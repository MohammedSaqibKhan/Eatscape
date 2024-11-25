package com.mohammedsaqibkhan.mealplanservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "meal_plan_entries")
public class MealPlanEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mealPlanId;
    private Long mealId;
    private LocalDate date;
    private String mealType; // e.g., breakfast, lunch, dinner
    // getters and setters
}
