package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Availability;
import lombok.*;

import java.util.Arrays;
import java.util.Date;
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
  private Date stayFromDate;
  private Date stayToDate;
  private int minNight;
  private int maxNight;
  private List<Integer> arrivalDays;
  private List<Integer> departureDays;
  private long accommodationTypeId;
  private long accommodationId;

  public AvailabilityDTO(Availability availability) {
    this.availabilityId = availability.getAvailabilityId();
    this.stayFromDate = availability.getStayFromDate();
    this.stayToDate = availability.getStayToDate();
    this.minNight = availability.getMinNight();
    this.maxNight = availability.getMaxNight();
    this.arrivalDays = convertDaysStringToList(availability.getArrivalDays());
    this.departureDays = convertDaysStringToList(availability.getDepartureDays());
    this.accommodationTypeId = availability.getAccommodationType().getAccommodationTypeId();
    this.accommodationId = availability.getAccommodation().getAccommodationId();
  }

  public List<Integer> convertDaysStringToList(String daysString) {
    return Arrays.stream(daysString.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }
}
