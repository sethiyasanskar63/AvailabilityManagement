package com.assignment.availabilitymanagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for transferring availability data, including validation to ensure data integrity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {

  @NotNull(message = "Availability ID must be provided.")
  @Positive(message = "Availability ID must be a positive number.")
  private Long availabilityId;

  @NotNull(message = "Stay from date must be provided.")
  @FutureOrPresent(message = "Stay from date must be in the future or present.")
  private LocalDate stayFromDate;

  @NotNull(message = "Stay to date must be provided.")
  @FutureOrPresent(message = "Stay to date must be in the future or present.")
  private LocalDate stayToDate;

  @NotNull(message = "Minimum night must be provided.")
  @Positive(message = "Minimum night must be a positive number.")
  private int minNight;

  private int[] arrivalDays;
  private int[] departureDays;

  @NotNull(message = "Accommodation Type ID must be provided.")
  private Long accommodationTypeId;

  @ReadOnlyProperty
  private LocalDateTime closingDate;
}
