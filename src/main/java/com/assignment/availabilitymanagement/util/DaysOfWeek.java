package com.assignment.availabilitymanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Utility class for handling days of the week using a bitmask. This approach allows for efficient
 * storage and retrieval of day selections. Methods in this class convert between arrays of day
 * constants and a single integer bitmask, where each bit represents a day of the week.
 */
public class DaysOfWeek {

  private static final Logger logger = LoggerFactory.getLogger(DaysOfWeek.class);

  public static final int MONDAY = 1;
  public static final int TUESDAY = 2;
  public static final int WEDNESDAY = 3;
  public static final int THURSDAY = 4;
  public static final int FRIDAY = 5;
  public static final int SATURDAY = 6;
  public static final int SUNDAY = 7;

  /**
   * Converts an array of days into a bitmask representing the selection of days.
   *
   * @param selectedDays Days to be set, represented by their corresponding integer values.
   * @return An integer bitmask representing the selected days.
   * @throws RuntimeException if an invalid day is provided or any other error occurs.
   */
  public static int setDays(int... selectedDays) {
    int bitmask = 0;
    try {
      for (int day : selectedDays) {
        if (day < MONDAY || day > SUNDAY) {
          throw new IllegalArgumentException("Invalid day: " + day);
        }
        bitmask |= (1 << (day - 1));
      }
      return bitmask;
    } catch (Exception e) {
      logger.error("Exception while setting days in bitmask", e);
      throw new RuntimeException("Exception while setting days in bitmask", e);
    }
  }

  /**
   * Retrieves the selected days from a bitmask.
   *
   * @param bitmask An integer bitmask representing the selected days.
   * @return An array of integers, each representing a selected day.
   * @throws RuntimeException if there is an error processing the bitmask.
   */
  public static int[] getSelectedDays(int bitmask) {
    try {
      return Arrays.stream(new int[]{MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY})
          .filter(day -> (bitmask & (1 << (day - 1))) != 0)
          .toArray();
    } catch (Exception e) {
      logger.error("Exception while getting selected days from bitmask", e);
      throw new RuntimeException("Exception while getting selected days from bitmask", e);
    }
  }
}
