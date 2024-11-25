package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.dto.MealPlanOverview;
import com.mohammedsaqibkhan.mealplanservice.repository.MealPlanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealPlanOverviewService {
    private final MealPlanRepository mealPlanRepository;

    public MealPlanOverviewService(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    public List<MealPlanOverview> getMealPlanOverview(Long userId) {
        List<MealPlanOverview> overview = new ArrayList<>();

        long totalPlans = mealPlanRepository.countAllPlans(userId);
        long activePlans = mealPlanRepository.countActivePlans(userId);
        long upcomingPlans = mealPlanRepository.countUpcomingPlans(userId);
        long completedPlans = mealPlanRepository.countCompletedPlans(userId);

        // Add overview data
        overview.add(new MealPlanOverview("Total Plans", String.valueOf(totalPlans), "up"));
        overview.add(new MealPlanOverview("Active Plans", String.valueOf(activePlans), "neutral"));
        overview.add(new MealPlanOverview("Upcoming Plans", String.valueOf(upcomingPlans), "up"));
        overview.add(new MealPlanOverview("Completed Plans", String.valueOf(completedPlans), "down"));

        return overview;
    }
}

