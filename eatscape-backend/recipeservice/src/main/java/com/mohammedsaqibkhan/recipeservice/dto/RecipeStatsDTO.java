package com.mohammedsaqibkhan.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeStatsDTO {
    private String title; // e.g., "Total Recipes"
    private long value;   // e.g., 150
    private String trend; // e.g., "up", "down", or "neutral"
    private String interval; // e.g., "Last 30 days"
}