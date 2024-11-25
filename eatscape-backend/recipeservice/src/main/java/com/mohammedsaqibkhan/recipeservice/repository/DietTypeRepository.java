package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietTypeRepository extends JpaRepository<DietType, Long> {
    // You can add custom queries as needed, for example:
    List<DietType> findByNameContaining(String name);
}
