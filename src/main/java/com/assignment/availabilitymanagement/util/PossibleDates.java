package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class PossibleDates {

  private static final Logger logger = LoggerFactory.getLogger(PossibleDates.class);

  public static List<Map<String, Object>> getPossibleDatesByAccommodationTypeId(Integer year, List<Availability> availabilities) {
    List<Map<String, Object>> possibleDates = new ArrayList<>();

    try {
      for (LocalDate currentDate = LocalDate.of(year, 1, 1); !currentDate.isAfter(LocalDate.of(year, 12, 31)); currentDate = currentDate.plusDays(1)) {
        if (isPossibleArrivalDate(currentDate, availabilities)) {
          for (LocalDate departureDate = currentDate; !departureDate.isAfter(LocalDate.of(year, 12, 31)); departureDate = departureDate.plusDays(1)) {
            if (isPossibleDepartureDate(currentDate, departureDate, availabilities)) {
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
    } catch (Exception e) {
      logger.error("Error while getting possible dates by accommodation type ID", e);
      throw new RuntimeException("Error while getting possible dates by accommodation type ID", e);
    }
  }

  private static boolean isPossibleArrivalDate(LocalDate currentDate, List<Availability> availabilities) {
    int dayOfWeek = currentDate.getDayOfWeek().getValue();

    try {
      for (Availability availability : availabilities) {
        LocalDate stayFromDate = availability.getStayFromDate();
        LocalDate stayToDate = availability.getStayToDate();

        if ((stayFromDate.isEqual(currentDate) || stayFromDate.isBefore(currentDate)) && (stayToDate.isEqual(currentDate) || stayToDate.isAfter(currentDate))) {
          return Arrays.stream(DaysOfWeek.getSelectedDays(availability.getArrivalDays())).anyMatch(day -> day == dayOfWeek);
        }
      }
      Availability defaultAvailability = createDefaultAvailability();
      return Arrays.stream(DaysOfWeek.getSelectedDays(defaultAvailability.getArrivalDays())).anyMatch(day -> day == dayOfWeek);
    } catch (Exception e) {
      logger.error("Error while checking possible arrival date", e);
      throw new RuntimeException("Error while checking possible arrival date", e);
    }
  }

  private static boolean isPossibleDepartureDate(LocalDate currentDate, LocalDate departureDate, List<Availability> availabilities) {
    int dayOfWeek = departureDate.getDayOfWeek().getValue();

    try {
      for (Availability availability : availabilities) {
        LocalDate stayFromDate = availability.getStayFromDate();
        LocalDate stayToDate = availability.getStayToDate();

        if ((stayFromDate.isEqual(departureDate) || stayFromDate.isBefore(departureDate)) && (stayToDate.isEqual(departureDate) || stayToDate.isAfter(departureDate))) {
          return Arrays.stream(DaysOfWeek.getSelectedDays(availability.getDepartureDays())).anyMatch(day -> day == dayOfWeek) && ChronoUnit.DAYS.between(currentDate, departureDate) >= availability.getMinNight() - 1;
        }
      }
      Availability defaultAvailability = createDefaultAvailability();
      return Arrays.stream(DaysOfWeek.getSelectedDays(defaultAvailability.getDepartureDays())).anyMatch(day -> day == dayOfWeek) && ChronoUnit.DAYS.between(currentDate, departureDate) >= defaultAvailability.getMinNight() - 1;
    } catch (Exception e) {
      logger.error("Error while checking possible departure date", e);
      throw new RuntimeException("Error while checking possible departure date", e);
    }
  }

  private static Availability createDefaultAvailability() {
    try {
      Availability defaultAvailability = new Availability();
      defaultAvailability.setArrivalDays(DaysOfWeek.setDays(DaysOfWeek.MONDAY, DaysOfWeek.TUESDAY, DaysOfWeek.WEDNESDAY, DaysOfWeek.THURSDAY, DaysOfWeek.FRIDAY, DaysOfWeek.SATURDAY, DaysOfWeek.SUNDAY));
      defaultAvailability.setDepartureDays(DaysOfWeek.setDays(DaysOfWeek.MONDAY, DaysOfWeek.TUESDAY, DaysOfWeek.WEDNESDAY, DaysOfWeek.THURSDAY, DaysOfWeek.FRIDAY, DaysOfWeek.SATURDAY, DaysOfWeek.SUNDAY));
      defaultAvailability.setMinNight(0);
      return defaultAvailability;
    } catch (Exception e) {
      logger.error("Error while creating default availability", e);
      throw new RuntimeException("Error while creating default availability", e);
    }
  }
}
