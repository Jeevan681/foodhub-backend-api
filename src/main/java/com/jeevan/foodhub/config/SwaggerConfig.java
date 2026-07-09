package com.jeevan.foodhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI foodHubOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("FoodHub API")
                        .description("Backend APIs for FoodHub Food Delivery Application")
                        .version("1.0")
                );
    }
}