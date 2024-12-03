package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.dto.MealDto;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlan;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanEntryRepository;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


//    public void deleteMealPlanEntriesByMealPlanId(Long mealPlanId) {
//        List<MealPlanEntry> entries = mealPlanEntryRepository.findByMealPlanId(mealPlanId);
//        if (entries.isEmpty()) {
//            throw new MealPlanNotFoundException("No meal plan entries found with mealPlanId: " + mealPlanId);
//        }
//        mealPlanEntryRepository.deleteAll(entries);
//    }

//    public List<MealPlanEntry> getMealPlanEntriesByMealPlanId(Long mealPlanId) {
//        return mealPlanEntryRepository.findByMealPlanId(mealPlanId);
//    }



    public List<MealPlan> getMealPlansByUser(Long userId) {
        // Hardcoded user ID logic (replace this with authenticated user later)
        return mealPlanRepository.findByUserId(userId);
    }


    public List<MealPlanEntry> addMealsToPlan(String mealPlanName, List<MealPlanEntry> entries) {
        return null;
    }









}
