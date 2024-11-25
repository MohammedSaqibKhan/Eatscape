package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.dto.MealDto;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlan;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import com.mohammedsaqibkhan.mealplanservice.exception.MealPlanNotFoundException;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanEntryRepository;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;
    @Autowired
    private MealPlanEntryRepository mealPlanEntryRepository;
    @Autowired
    private MealServiceClient mealServiceClient;

    public MealDto getMealDetails(Long mealId) {
        return mealServiceClient.getMealById(mealId);
    }

    public void addMealToPlanWithDetails(Long mealPlanId, Long mealId) {
        MealDto mealDto = mealServiceClient.getMealById(mealId);
        // Use mealDto as needed, such as saving details in MealPlanEntry or logging details

    }

    public MealPlan createMealPlan(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }

//    public List<MealPlanEntry> addMealsToPlan(Long mealPlanId, List<MealPlanEntry> meals) {
//
//        System.out.println("mealPlanId: " + mealPlanId);
//
//        MealPlan mealPlan = mealPlanRepository.findByUserId(mealPlanId);
//
//        if (!Objects.equals(mealPlan.getUserId(), mealPlanId)) {
//            throw new MealPlanNotFoundException("Meal Plan not found with id: " + mealPlanId);
//        }
//        System.out.println(mealPlan.getId());
//        System.out.println(mealPlan.getUserId());
//        meals.forEach(meal -> System.out.println(meal.getMealPlanId()));
//        meals.forEach(meal -> meal.setMealPlanId(mealPlanId));
//        return mealPlanEntryRepository.saveAll(meals);
//    }
//
//    public void deleteMealPlanByUserId(Long userId) {
//        MealPlan mealPlan = mealPlanRepository.findByUserId(userId);
//        if (mealPlan == null) {
//            throw new MealPlanNotFoundException("Meal Plan not found with user ID: " + userId);
//        }
//        deleteMealPlanEntriesByMealPlanId(userId);
//        mealPlanRepository.delete(mealPlan);
//    }


    public void deleteMealPlanEntriesByMealPlanId(Long mealPlanId) {
        List<MealPlanEntry> entries = mealPlanEntryRepository.findByMealPlanId(mealPlanId);
        if (entries.isEmpty()) {
            throw new MealPlanNotFoundException("No meal plan entries found with mealPlanId: " + mealPlanId);
        }
        mealPlanEntryRepository.deleteAll(entries);
    }

    public List<MealPlanEntry> getMealPlanEntriesByMealPlanId(Long mealPlanId) {
        return mealPlanEntryRepository.findByMealPlanId(mealPlanId);
    }


//    public MealPlan findOrCreateMealPlan(Long userId) {
//        return mealPlanRepository.findByUserId(userId)
//                .orElseGet(() -> {
//                    MealPlan newPlan = new MealPlan();
//                    newPlan.setUserId(userId);
//                    newPlan.setName("Default Plan");
//                    newPlan.setStartDate(LocalDate.now());
//                    newPlan.setEndDate(LocalDate.now().plusWeeks(1));
//                    return mealPlanRepository.save(newPlan);
//                });
//    }

    public MealPlanEntry addMealToPlan(Long mealPlanId, Long mealId) {
        MealPlanEntry entry = new MealPlanEntry();
        entry.setMealPlanId(mealPlanId);
        entry.setMealId(mealId);
        entry.setMealType("Breakfast");
        entry.setDate(LocalDate.now());
        return mealPlanEntryRepository.save(entry);
    }


    public List<MealPlan> getMealPlansByUser(Long userId) {
        // Hardcoded user ID logic (replace this with authenticated user later)
        return mealPlanRepository.findByUserId(userId);
    }


    public void deleteMealPlanById(Long mealPlanId) {
        deleteMealPlanEntriesByMealPlanId(mealPlanId);
        mealPlanRepository.deleteById(mealPlanId);
    }

    public List<MealPlanEntry> addMealsToPlan(String mealPlanName, List<MealPlanEntry> entries) {
        return null;
    }
}
