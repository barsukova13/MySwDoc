package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new ProcessHandle.Info()
                        .title("User Service API")
                        .version("1.0")
                        .description("API for managing users")
                        .contact(new Contact()
                                .name("Support")
                                .email("support@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("User Service Documentation")
                        .url("https://example.com/docs"));
    }

}
