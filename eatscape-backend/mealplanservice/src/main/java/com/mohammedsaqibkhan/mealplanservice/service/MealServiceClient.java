package com.mohammedsaqibkhan.mealplanservice.service;

import com.mohammedsaqibkhan.mealplanservice.dto.MealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "meal-service", url = "http://localhost:8083/meals")
public interface MealServiceClient {
    @GetMapping("/{mealId}")
    MealDto getMealById(@PathVariable Long mealId);
}

