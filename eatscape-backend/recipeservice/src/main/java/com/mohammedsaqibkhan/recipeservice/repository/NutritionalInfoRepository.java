package com.mohammedsaqibkhan.recipeservice.repository;

import com.mohammedsaqibkhan.recipeservice.entity.NutritionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionalInfoRepository extends JpaRepository<NutritionalInfo, Long> {
    // Custom queries if needed
    Optional<NutritionalInfo> findByRecipeId(Long recipeId);
}
