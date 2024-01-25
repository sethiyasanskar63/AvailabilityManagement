package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.entity.DaysOfWeek;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class AvailabilityDTO {

  private long availabilityId;
  private LocalDate stayFromDate;
  private LocalDate stayToDate;
  private int minNight;
  private int[] arrivalDays;
  private int[] departureDays;
  private Long accommodationTypeId;

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
