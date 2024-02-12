package com.assignment.availabilitymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for conveying Resort data between processes.
 * Includes validations to ensure data integrity when creating or updating Resort information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResortDTO {

  @NotNull(message = "Resort ID is required and must be positive.")
  @Positive(message = "Resort ID must be a positive number.")
  private Long resortId;

  @NotBlank(message = "Resort Name is required and cannot be blank.")
  private String resortName;
}
