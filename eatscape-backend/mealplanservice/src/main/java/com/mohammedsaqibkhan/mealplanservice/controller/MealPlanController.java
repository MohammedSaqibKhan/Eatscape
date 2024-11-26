package com.mohammedsaqibkhan.mealplanservice.controller;

import com.mohammedsaqibkhan.mealplanservice.dto.MealDto;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlan;
import com.mohammedsaqibkhan.mealplanservice.entity.MealPlanEntry;
import com.mohammedsaqibkhan.mealplanservice.exception.MealPlanNotFoundException;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanEntryRepository;
import com.mohammedsaqibkhan.mealplanservice.service.MealPlanEntryService;
import com.mohammedsaqibkhan.mealplanservice.service.MealPlanService;
import com.mohammedsaqibkhan.mealplanservice.service.MealServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mealplans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

//    @GetMapping("/meal-plans")
//    public ResponseEntity<List<MealPlan>> getMealPlans() {
//        MealPlan createdPlan = mealPlanService.get(mealPlan);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
//    }



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
//
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteMealPlanByUserId(@PathVariable Long userId) {
//        try {
//            mealPlanService.deleteMealPlanByUserId(userId);
//            return ResponseEntity.noContent().build(); // Returns 204 No Content on successful deletion
//        } catch (MealPlanNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found if meal plan not found
//        }
//    }


//    @GetMapping("/{mealPlanId}/entries")
//    public ResponseEntity<List<MealPlanEntry>> getMealPlanEntriesByMealPlanId(@PathVariable Long mealPlanId) {
//        List<MealPlanEntry> entries = mealPlanService.getMealPlanEntriesByMealPlanId(mealPlanId);
//        return ResponseEntity.ok(entries);
//    }

//    @DeleteMapping("/{mealPlanId}/entries")
//    public ResponseEntity<Void> deleteMealPlanEntriesByMealPlanId(@PathVariable Long mealPlanId) {
//        try {
//            mealPlanService.deleteMealPlanEntriesByMealPlanId(mealPlanId);
//            return ResponseEntity.noContent().build(); // 204 No Content if deletion is successful
//        } catch (MealPlanNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if entries are not found
//        }
//    }



//    @PostMapping("/{userId}/{mealId}/addMeal")
//    public ResponseEntity<MealPlanEntry> addMealToPlan(
//            @PathVariable Long userId,
//            @PathVariable Long mealId) {
//        MealPlan mealPlan = mealPlanService.findOrCreateMealPlan(userId);
//        System.out.println(mealPlan.toString());
//        MealPlanEntry entry = mealPlanService.addMealToPlan(mealPlan.getId(), mealId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
//    }

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
//
//    @DeleteMapping("/{mealPlanId}")
//    public ResponseEntity<?> deleteMealPlanById(@PathVariable Long mealPlanId) {
//        mealPlanService.deleteMealPlanById(mealPlanId);
//        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
//    }

    @Autowired
    private MealPlanEntryService mealPlanEntryService;

//    @GetMapping("mindmap/user/{userId}")
//    public MealPlanResponse getMealPlanForUser(@PathVariable Long userId) {
//        List<MealPlan> mealPlans = mealPlanService.getMealPlansByUser(userId);
//        List<MealPlanEntry> mealPlanEntries = new ArrayList<>();
//        for (MealPlan mealPlan : mealPlans) {
//            List<MealPlanEntry> entriesForPlan = mealPlanEntryService.getMealPlanEntriesForMealPlan(mealPlan.getId());
//            mealPlanEntries.addAll(entriesForPlan);
//        }
//        return new MealPlanResponse(mealPlans, mealPlanEntries);
//    }

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

//    @GetMapping("mindmap/mealplan/{mealPlanId}/entries")
//    public List<MealPlanEntry> getMealPlanEntriesForMealPlan(@PathVariable Long mealPlanId) {
//        return mealPlanEntryService.getMealPlanEntriesForMealPlan(mealPlanId);
//    }







}
