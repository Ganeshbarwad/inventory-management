package com.gt.inventory_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI inventoryManagementAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Management API")
                        .version("1.0.0")
                        .description("API documentation for Inventory & Stock Management System")
                        .contact(new Contact()
                                .name("Ganesh Barwad Reddy")
                                .email("ganesh@example.com")
                                .url("https://github.com/ganesh"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                // ✅ JWT Bearer support for testing secured APIs
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
