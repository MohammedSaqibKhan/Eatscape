package com.mohammedsaqibkhan.mealplanservice.controller;


import com.mohammedsaqibkhan.mealplanservice.service.MealPlanEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mealplanentries")
public class MealPlanEntryController {

    @Autowired
    private MealPlanEntryService service;

    @DeleteMapping("/{mealEntryId}")
    public ResponseEntity<?> deleteMealPlanEntry(@PathVariable Long mealEntryId) {
        service.deleteMealPlanEntry(mealEntryId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
    }

}
