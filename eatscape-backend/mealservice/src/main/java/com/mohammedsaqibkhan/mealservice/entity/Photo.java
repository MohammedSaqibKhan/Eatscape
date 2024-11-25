package com.mohammedsaqibkhan.mealservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Photo {


    private String highres;

    @JsonProperty("is_user_uploaded")
    private boolean isUserUploaded;

    // Getters and setters
}

