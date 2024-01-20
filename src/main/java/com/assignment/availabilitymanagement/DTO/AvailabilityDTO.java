package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Availability;
import lombok.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AvailabilityDTO {

  private long availabilityId;
  private LocalDate stayFromDate;
  private LocalDate stayToDate;
  private int minNight;
  private List<Integer> arrivalDays;
  private List<Integer> departureDays;
  private Long accommodationTypeId;
  private Long accommodationId;

  public AvailabilityDTO(Availability availability) {
    this.availabilityId = availability.getAvailabilityId();
    this.stayFromDate = availability.getStayFromDate();
    this.stayToDate = availability.getStayToDate();
    this.minNight = availability.getMinNight();
    this.arrivalDays = convertDaysStringToList(availability.getArrivalDays());
    this.departureDays = convertDaysStringToList(availability.getDepartureDays());
    this.accommodationTypeId = availability.getAccommodationType() == null ? null : availability.getAccommodationType().getAccommodationTypeId();
    this.accommodationId = availability.getAccommodation() == null ? null : availability.getAccommodation().getAccommodationId();
  }

  public AvailabilityDTO(Long availabilityId, LocalDate stayFromDate, LocalDate stayToDate, Integer minNight, String arrivalDays, String departureDays, Long accommodationTypeId, Long accommodationId) {
    this.availabilityId = availabilityId;
    this.stayFromDate = stayFromDate;
    this.stayToDate = stayToDate;
    this.minNight = minNight;
    this.arrivalDays = convertDaysStringToList(arrivalDays);
    this.departureDays = convertDaysStringToList(departureDays);
    this.accommodationTypeId = accommodationTypeId;
    this.accommodationId = accommodationId;
  }

  public List<Integer> convertDaysStringToList(String daysString) {
    return Arrays.stream(daysString.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }
}
