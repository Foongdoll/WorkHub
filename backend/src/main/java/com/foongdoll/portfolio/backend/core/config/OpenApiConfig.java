package com.foongdoll.portfolio.backend.core.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        String schemeName = "BearerAuth";

        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Foongdoll Portfolio API")
                        .version("v1")
                        .description("Auth / Company / Approval API"))
                .components(new Components().addSecuritySchemes(schemeName, bearerScheme))
                .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
