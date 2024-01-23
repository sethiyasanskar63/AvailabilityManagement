package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PossibleDates {

  public static List<Map<String, Object>> getPossibleDatesByAccommodationTypeId(Long accommodationTypeId, Integer year, List<Availability> availabilities) {
    List<Map<String, Object>> possibleDates = new ArrayList<>();

    for (LocalDate currentDate = LocalDate.of(year, 1, 1); !currentDate.isAfter(LocalDate.of(year, 12, 31)); currentDate = currentDate.plusDays(1)) {
      if (isPossibleArrivalDate(currentDate, availabilities)) {
        for (LocalDate departureDate = currentDate; !departureDate.isAfter(LocalDate.of(year, 12, 31)); departureDate = departureDate.plusDays(1)) {
          if (isPossibleDepartureDate(departureDate, availabilities) && ChronoUnit.DAYS.between(currentDate, departureDate) >= 3L) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("AccommodationTypeId", availabilities.get(0).getAccommodationType().getAccommodationTypeId());
            entry.put("ArrivalDate", currentDate.format(DateTimeFormatter.ISO_DATE));
            entry.put("DepartureDate", departureDate.format(DateTimeFormatter.ISO_DATE));
            possibleDates.add(entry);
          }
        }
      }
    }

    return possibleDates;
  }

  private static boolean isPossibleArrivalDate(LocalDate currentDate, List<Availability> availabilities) {
    int dayOfWeek = currentDate.getDayOfWeek().getValue();

    for (Availability availability : availabilities) {
      LocalDate stayFromDate = availability.getStayFromDate();
      LocalDate stayToDate = availability.getStayToDate();

      if ((stayFromDate.isEqual(currentDate) || stayFromDate.isBefore(currentDate)) &&
          (stayToDate.isEqual(currentDate) || stayToDate.isAfter(currentDate))) {

        if (availability.getArrivalDays().contains(String.valueOf(dayOfWeek)) || availability.getArrivalDays().contains("8")) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean isPossibleDepartureDate(LocalDate departureDate, List<Availability> availabilities) {
    int dayOfWeek = departureDate.getDayOfWeek().getValue();

    for (Availability availability : availabilities) {
      LocalDate stayFromDate = availability.getStayFromDate();
      LocalDate stayToDate = availability.getStayToDate();

      if ((stayFromDate.isEqual(departureDate) || stayFromDate.isBefore(departureDate)) &&
          (stayToDate.isEqual(departureDate) || stayToDate.isAfter(departureDate))) {

        if (availability.getDepartureDays().contains(String.valueOf(dayOfWeek)) || availability.getDepartureDays().contains("8")) {
          return true;
        }
      }
    }

    return false;
  }
}