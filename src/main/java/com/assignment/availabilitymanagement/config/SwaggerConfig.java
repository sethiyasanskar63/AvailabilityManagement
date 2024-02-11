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
 * Configures Swagger/OpenAPI documentation for the Availability Management API.
 * This configuration sets up the API information, security schemes (such as JWT bearer token),
 * and provides external documentation links.
 */
@Configuration
public class SwaggerConfig {

  private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

  /**
   * Creates a security scheme for the API using JWT bearer tokens.
   * This method defines how the security scheme for API authentication is presented in the Swagger UI.
   *
   * @return The configured SecurityScheme object.
   */
  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer")
        .description("JWT Bearer Token Authentication");
  }

  /**
   * Configures and creates the OpenAPI object for Swagger documentation.
   * This includes API information such as title, description, version, and license.
   * Additionally, it sets up the security scheme for JWT authentication and links to external documentation.
   *
   * @return The configured OpenAPI object.
   */
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
        .info(new Info()
            .title("Availability Management API")
            .description("API for managing availability of accommodations, including CRUD operations and availability queries.")
            .version("v1.0")
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")))
        .externalDocs(new ExternalDocumentation()
            .description("Availability Management GitHub Repository")
            .url("https://github.com/sethiyasanskar63/AvailabilityManagement"));
  }
}
