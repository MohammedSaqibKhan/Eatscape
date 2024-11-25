package com.mohammedsaqibkhan.mealservice.service;

import com.mohammedsaqibkhan.mealservice.entity.NutritionRes;
import com.mohammedsaqibkhan.mealservice.model.Meal;
import com.mohammedsaqibkhan.mealservice.model.NutritionRequest;
import com.mohammedsaqibkhan.mealservice.model.NutritionResponse;
import com.mohammedsaqibkhan.mealservice.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mohammedsaqibkhan.mealservice.model.Food;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${nutrition.service.url}")
    private String nutritionServiceUrl;

    @Value("${nutritionix.app.id}")
    private String appId;

    @Value("${nutritionix.app.key}")
    private String appKey;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }


    public NutritionRes getMealById(Long id) {
        Meal meal = mealRepository.findById(id).orElse(null);
        NutritionRes nutritionData = getfullNutritionData(meal.getIngredients());
        System.out.println(nutritionData.toString());
        return nutritionData;
    }

    private NutritionRes getfullNutritionData(String ingredients) {
        // Build request for Nutritionix API
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.set("Content-Type", "application/json");

        String requestJson = "{ \"query\": \"" + ingredients + "\", \"timezone\": \"Asia/Kolkata\" }";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<NutritionRes> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, NutritionRes.class);

        return response.getBody();
    }




    public Meal createMeal(Meal meal) {
        // Fetch nutrition details from Nutritionix
        NutritionResponse nutritionData = getNutritionData(meal.getIngredients());
        System.out.println(nutritionData.toString());

        if (nutritionData != null && !nutritionData.getFoods().isEmpty()) {
            // Aggregate nutrition data as needed
            double totalCalories = nutritionData.getFoods().stream()
                    .mapToDouble(Food::getNf_calories)
                    .sum();

            double totalCarbs = nutritionData.getFoods().stream()
                    .mapToDouble(Food::getNf_total_carbohydrate)
                    .sum();

            double totalFats = nutritionData.getFoods().stream()
                    .mapToDouble(Food::getNf_total_fat)
                    .sum();

            double totalProteins = nutritionData.getFoods().stream()
                    .mapToDouble(Food::getNf_protein)
                    .sum();

            meal.setCalories( totalCalories);
            meal.setCarbs(totalCarbs);
            meal.setFat(totalFats);
            meal.setProtein( totalProteins);
            // (Optional) Add other nutritional data to the Meal entity if required
        }

        return mealRepository.save(meal);
    }

    private NutritionResponse getNutritionData(String ingredients) {
        // Build request for Nutritionix API
        String url = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.set("Content-Type", "application/json");

        String requestJson = "{ \"query\": \"" + ingredients + "\", \"timezone\": \"Asia/Kolkata\" }";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<NutritionResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, NutritionResponse.class);

        return response.getBody();
    }


    public Meal updateMeal(String name, Meal mealDetails) {
        Optional<Meal> meal = mealRepository.findByName(name);
        if (meal.isPresent()) {
            Meal mealFromDb = meal.get();
            mealFromDb.setName(mealDetails.getName());
            mealFromDb.setDescription(mealDetails.getDescription());
            mealFromDb.setCalories(mealDetails.getCalories());
            mealFromDb.setIngredients(mealDetails.getIngredients());
            mealFromDb.setDietType(mealDetails.getDietType());
            return mealRepository.save(mealFromDb);
        }
        return null;
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public Meal getMealByName(String name) {
        Optional<Meal> meal = mealRepository.findByName(name);
        return meal.orElse(null);
    }

    public void deleteMealsByIds(List<Long> ids) {
        mealRepository.deleteAllById(ids);
    }

//    private NutritionResponse getNutritionData(NutritionRequest nutritionRequest) {
//        return restTemplate.postForObject(
//                nutritionServiceUrl + "/analyze",
//                nutritionRequest,
//                NutritionResponse.class
//        );
//    }
}
