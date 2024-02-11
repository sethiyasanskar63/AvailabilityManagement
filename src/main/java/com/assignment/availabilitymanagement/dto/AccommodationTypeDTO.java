package com.assignment.availabilitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for conveying accommodation type information between processes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeDTO {

  @NotNull(message = "Accommodation Type ID is required.")
  @Positive(message = "Accommodation Type ID must be a positive number.")
  private long accommodationTypeId;

  @NotBlank(message = "Accommodation Type Name is required and cannot be blank.")
  private String accommodationTypeName;

  @NotNull(message = "Resort ID is required.")
  @Positive(message = "Resort ID must be a positive number.")
  private long resortId;
}
