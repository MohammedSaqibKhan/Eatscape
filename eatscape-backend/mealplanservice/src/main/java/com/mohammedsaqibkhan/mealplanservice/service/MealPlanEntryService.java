package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MealPlanEntryService {

    @Autowired
    private MealPlanEntryRepository mealPlanEntryRepository;



    public void deleteMealPlanEntry(Long mealEntryId) {
        mealPlanEntryRepository.deleteById(mealEntryId);
    }

    public void addMealPlanEntry(MealPlanEntry mealPlanEntry) {
        mealPlanEntryRepository.save(mealPlanEntry);
    }
}
