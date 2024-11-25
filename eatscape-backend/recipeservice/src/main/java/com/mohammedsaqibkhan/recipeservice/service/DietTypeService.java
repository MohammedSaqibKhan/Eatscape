package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import java.util.List;

public interface DietTypeService {
    DietType createDietType(DietType dietType);

    List<DietType> getAllDietTypes();

    DietType getDietTypeById(Long id);

    DietType updateDietType(Long id, DietType dietType);

    void deleteDietType(Long id);
}