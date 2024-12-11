package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.Recipe;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecipeSpecifications {

    public static Specification<Recipe> withFilters(
            String query, List<String> mealTypes, List<String> dietTypes,
            Integer minCalories, Integer maxCalories, Integer minCarbs, Integer maxCarbs,
            Integer minProtein, Integer maxProtein
    ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by query (name)
            if (query != null && !query.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + query.toLowerCase() + "%"
                ));
            }

            // Filter by meal types (match MealType.name)
            if (mealTypes != null && !mealTypes.isEmpty()) {
                predicates.add(root.join("mealType").get("name").in(mealTypes));
            }

            // Filter by diet types (match DietType.name)
            if (dietTypes != null && !dietTypes.isEmpty()) {
                predicates.add(root.join("dietType").get("name").in(dietTypes));
            }

            // Join with NutritionalInfo to filter by nutritional ranges
            Join<Object, Object> nutritionalInfo = root.join("nutritionalInfo");

            if (minCalories != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(nutritionalInfo.get("calories"), minCalories));
            }
            if (maxCalories != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(nutritionalInfo.get("calories"), maxCalories));
            }
            if (minCarbs != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(nutritionalInfo.get("totalCarbohydrates"), minCarbs));
            }
            if (maxCarbs != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(nutritionalInfo.get("totalCarbohydrates"), maxCarbs));
            }
            if (minProtein != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(nutritionalInfo.get("protein"), minProtein));
            }
            if (maxProtein != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(nutritionalInfo.get("protein"), maxProtein));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}