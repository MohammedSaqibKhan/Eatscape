package com.mohammedsaqibkhan.recipeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomRestTemplateConfig {

    @Bean(name = "formRestTemplate")
    public RestTemplate formRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        return restTemplate;
    }
}
