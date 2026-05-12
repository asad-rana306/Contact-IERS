package com.Contact.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        // 1. For when you are routing through the API Gateway (Local or Deployed)
                        new Server().url("/contacts").description("API Gateway Route"),

                        // 2. For when you are hitting the microservice directly on port 8081
                        new Server().url("/").description("Direct Service")
                ));
    }
}