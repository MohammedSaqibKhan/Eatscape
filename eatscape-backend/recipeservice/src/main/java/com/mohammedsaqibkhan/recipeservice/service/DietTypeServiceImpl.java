package com.mohammedsaqibkhan.recipeservice.service;

import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import com.mohammedsaqibkhan.recipeservice.exception.ResourceNotFoundException;
import com.mohammedsaqibkhan.recipeservice.repository.DietTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DietTypeServiceImpl implements DietTypeService {
    private final DietTypeRepository dietTypeRepository;

    @Override
    public DietType createDietType(DietType dietType) {
        return dietTypeRepository.save(dietType);
    }

    @Override
    public List<DietType> getAllDietTypes() {
        return dietTypeRepository.findAll();
    }

    @Override
    public DietType getDietTypeById(Long id) {
        return dietTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DietType not found with ID: " + id));
    }

    @Override
    public DietType updateDietType(Long id, DietType dietType) {
        DietType existingDietType = dietTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DietType not found with ID: " + id));
        dietType.setId(existingDietType.getId());
        return dietTypeRepository.save(dietType);
    }

    @Override
    public void deleteDietType(Long id) {
        if (!dietTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("DietType not found with ID: " + id);
        }
        dietTypeRepository.deleteById(id);
    }
}