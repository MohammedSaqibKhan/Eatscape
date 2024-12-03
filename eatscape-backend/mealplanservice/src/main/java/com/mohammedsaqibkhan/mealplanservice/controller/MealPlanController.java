package com.mohammedsaqibkhan.mealplanservice.controller;

import com.mohammedsaqibkhan.mealplanservice.entity.MealPlan;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import com.mohammedsaqibkhan.mealplanservice.exception.MealPlanNotFoundException;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanEntryRepository;
import com.mohammedsaqibkhan.mealplanservice.service.MealPlanEntryService;
import com.mohammedsaqibkhan.mealplanservice.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mealplans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;



    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(@RequestBody MealPlan mealPlan) {
        MealPlan createdPlan = mealPlanService.createMealPlan(mealPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
    }

    @PostMapping("/{mealPlanName}")
    public ResponseEntity<List<MealPlanEntry>> addMealsToPlan(
            @PathVariable String mealPlanName,
            @RequestBody List<MealPlanEntry> entries) {
        try {
            System.out.println("mealPlanId: " + mealPlanName);
            List<MealPlanEntry> addedEntries = mealPlanService.addMealsToPlan(mealPlanName, entries);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedEntries);
        } catch (MealPlanNotFoundException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Autowired
    private MealPlanEntryRepository mealPlanEntryRepository;

    @PostMapping("/mealplan-entries")
    public ResponseEntity<MealPlanEntry> createMealPlanEntry(@RequestBody MealPlanEntry mealPlanEntry) {
        MealPlanEntry createdEntry = mealPlanEntryRepository.save(mealPlanEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealPlan>> getMealPlansByUser(@PathVariable Long userId) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByUser(userId);
        if (mealPlans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mealPlans);
    }

    @Autowired
    private MealPlanEntryService mealPlanEntryService;

    // Response DTO for meal plans and entries
    public static class MealPlanResponse {
        private List<MealPlan> mealPlans;
        private List<MealPlanEntry> mealPlanEntries;

        // Constructor to initialize meal plans and their entries
        public MealPlanResponse(List<MealPlan> mealPlans, List<MealPlanEntry> mealPlanEntries) {
            this.mealPlans = mealPlans;
            this.mealPlanEntries = mealPlanEntries;
        }

        // Getters and setters for mealPlans and mealPlanEntries
        public List<MealPlan> getMealPlans() {
            return mealPlans;
        }

        public void setMealPlans(List<MealPlan> mealPlans) {
            this.mealPlans = mealPlans;
        }

        public List<MealPlanEntry> getMealPlanEntries() {
            return mealPlanEntries;
        }

        public void setMealPlanEntries(List<MealPlanEntry> mealPlanEntries) {
            this.mealPlanEntries = mealPlanEntries;
        }
    }









}
