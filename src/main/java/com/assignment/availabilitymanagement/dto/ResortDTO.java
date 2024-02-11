package com.assignment.availabilitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
