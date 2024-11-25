package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.MealType;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.MealTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealTypeServiceImpl implements MealTypeService {
    private final MealTypeRepository mealTypeRepository;

    @Override
    public MealType createMealType(MealType mealType) {
        return mealTypeRepository.save(mealType);
    }

    @Override
    public List<MealType> getAllMealTypes() {
        return mealTypeRepository.findAll();
    }

    @Override
    public MealType getMealTypeById(Long id) {
        return mealTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealType not found with ID: " + id));
    }

    @Override
    public MealType updateMealType(Long id, MealType mealType) {
        MealType existingMealType = mealTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealType not found with ID: " + id));
        mealType.setId(existingMealType.getId());
        return mealTypeRepository.save(mealType);
    }

    @Override
    public void deleteMealType(Long id) {
        if (!mealTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("MealType not found with ID: " + id);
        }
        mealTypeRepository.deleteById(id);
    }
}