package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Availability;
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
  private List<Integer> arrivalDays;
  private List<Integer> departureDays;
  private Long accommodationTypeId;

  public AvailabilityDTO(Availability availability) {
    this.availabilityId = availability.getAvailabilityId();
    this.stayFromDate = availability.getStayFromDate();
    this.stayToDate = availability.getStayToDate();
    this.minNight = availability.getMinNight();
    this.arrivalDays = convertDaysStringToList(availability.getArrivalDays());
    this.departureDays = convertDaysStringToList(availability.getDepartureDays());
    this.accommodationTypeId = availability.getAccommodationType() == null ? null : availability.getAccommodationType().getAccommodationTypeId();
  }

  public List<Integer> convertDaysStringToList(String daysString) {
    return Arrays.stream(daysString.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }
}
