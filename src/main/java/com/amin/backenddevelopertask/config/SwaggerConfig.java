package com.amin.backenddevelopertask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend Developer Task API")
                        .version("1.0")
                        .description("API for managing products and cards")
                        .contact(new Contact()
                                .name("Amin Abasov")
                                .email("abbasovamin72@gmail.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .packagesToScan("com.amin.backenddevelopertask.controller")
                .build();
    }

}
