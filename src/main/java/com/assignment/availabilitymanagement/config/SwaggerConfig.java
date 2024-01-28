package com.assignment.availabilitymanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 * Author: Sanskar Sethiya
 */
@Configuration
public class SwaggerConfig {

  private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

  /**
   * Creates the API key security scheme for Swagger.
   *
   * @return The configured SecurityScheme.
   */
  private SecurityScheme createAPIKeyScheme() {
    try {
      return new SecurityScheme().type(SecurityScheme.Type.HTTP)
          .bearerFormat("JWT")
          .scheme("bearer");
    } catch (Exception e) {
      logger.error("Error creating API key security scheme for Swagger", e);
      throw e;
    }
  }

  /**
   * Configures the OpenAPI bean for Swagger documentation.
   *
   * @return The configured OpenAPI.
   */
  @Bean
  public OpenAPI openAPI() {
    try {
      return new OpenAPI().addSecurityItem(new SecurityRequirement()
              .addList("Bearer Authentication"))
          .components(new Components().addSecuritySchemes
              ("Bearer Authentication", createAPIKeyScheme()))
          .info(new Info().title("Availability Management API")
              .description("API for managing availability of accommodations.")
              .version("v1.0")
              .license(new License().name("Availability Management Application")
                  .url("https://github.com/sethiyasanskar63/AvailabilityManagement")))
          .externalDocs(new ExternalDocumentation()
              .description("Availability Management GitHub Project")
              .url("https://github.com/sethiyasanskar63/AvailabilityManagement/projects/3"));
    } catch (Exception e) {
      logger.error("Error configuring OpenAPI for Swagger", e);
      throw e;
    }
  }
}
