package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.util.DaysOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) for Availability entity.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {

  private long availabilityId;
  private LocalDate stayFromDate;
  private LocalDate stayToDate;
  private int minNight;
  private int[] arrivalDays;
  private int[] departureDays;
  private Long accommodationTypeId;

  /**
   * Constructs an AvailabilityDTO object using the information from an Availability entity.
   *
   * @param availability The Availability entity.
   */
  public AvailabilityDTO(Availability availability) {
    this.availabilityId = availability.getAvailabilityId();
    this.stayFromDate = availability.getStayFromDate();
    this.stayToDate = availability.getStayToDate();
    this.minNight = availability.getMinNight();
    this.arrivalDays = DaysOfWeek.getSelectedDays(availability.getArrivalDays());
    this.departureDays = DaysOfWeek.getSelectedDays(availability.getDepartureDays());
    this.accommodationTypeId = availability.getAccommodationType().getAccommodationTypeId();
  }
}
