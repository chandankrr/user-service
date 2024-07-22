package com.chandankrr.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI userServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("User Service API")
                        .description("This API handles the user services for Budget Buddy, a mobile application designed for tracking expenses. It includes endpoints for update, create, get user info, and more.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0")));
    }
}
