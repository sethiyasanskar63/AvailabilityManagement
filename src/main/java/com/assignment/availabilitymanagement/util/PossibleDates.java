package com.assignment.availabilitymanagement.util;

import com.assignment.availabilitymanagement.entity.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Utility class for calculating possible booking dates for accommodations.
 */
@Component
public class PossibleDates {

  private static final Logger logger = LoggerFactory.getLogger(PossibleDates.class);

  /**
   * Calculates all possible combinations of arrival and departure dates for a given accommodation type within a specified year.
   *
   * @param year           The year for which to calculate possible dates.
   * @param availabilities A list of {@link Availability} objects representing the available periods and constraints for booking.
   * @return A list of maps, each containing an accommodation type ID, arrival date, and departure date.
   */
  public static List<Map<String, Object>> getPossibleDatesByAccommodationTypeId(Integer year, List<Availability> availabilities) {
    List<Map<String, Object>> possibleDates = new ArrayList<>();

    try {
      for (LocalDate currentDate = LocalDate.of(year, 1, 1); !currentDate.isAfter(LocalDate.of(year, 12, 31)); currentDate = currentDate.plusDays(1)) {
        if (isPossibleArrivalDate(currentDate, availabilities)) {
          for (LocalDate departureDate = currentDate.plusDays(1); !departureDate.isAfter(LocalDate.of(year, 12, 31)); departureDate = departureDate.plusDays(1)) {
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
    } catch (Exception e) {
      logger.error("Error calculating possible dates for accommodation type ID", e);
      throw new IllegalStateException("Error calculating possible dates for accommodation type ID", e);
    }
    return possibleDates;
  }

  /**
   * Checks if a given date can be considered as a possible arrival date based on the specified availabilities.
   *
   * @param currentDate    The date to check.
   * @param availabilities A list of {@link Availability} objects with booking constraints.
   * @return {@code true} if the date is a possible arrival date; {@code false} otherwise.
   */
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

  /**
   * Checks if a given date range can be considered as a possible departure date based on the specified availabilities.
   *
   * @param currentDate    The potential arrival date.
   * @param departureDate  The date to check as a potential departure date.
   * @param availabilities A list of {@link Availability} objects with booking constraints.
   * @return {@code true} if the date range is a possible booking period; {@code false} otherwise.
   */
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

  /**
   * Creates a default {@link Availability} object to use when specific availabilities are not applicable.
   *
   * @return A default {@link Availability} object.
   */
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