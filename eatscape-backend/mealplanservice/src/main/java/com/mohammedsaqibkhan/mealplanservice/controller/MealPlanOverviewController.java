package com.mohammedsaqibkhan.mealplanservice.controller;

import com.mohammedsaqibkhan.mealplanservice.dto.MealPlanOverview;
import com.mohammedsaqibkhan.mealplanservice.service.MealPlanOverviewService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/overview")
public class MealPlanOverviewController {
    private final MealPlanOverviewService overviewService;

    public MealPlanOverviewController(MealPlanOverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping("/{userId}")
    public List<MealPlanOverview> getOverview(@PathVariable Long userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is missing.");
        }
        return overviewService.getMealPlanOverview(userId);
    }

}

