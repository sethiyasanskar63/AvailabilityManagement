package com.assignment.availabilitymanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Utility class for handling days of the week.
 * Author: Sanskar Sethiya
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
   * Sets the selected days in a bitmask.
   *
   * @param selectedDays Days to be set.
   * @return Bitmask representing the selected days.
   * @throws RuntimeException if there is an error while setting days in the bitmask
   */
  public static int setDays(int... selectedDays) {
    try {
      int bitmask = 0;
      for (int day : selectedDays) {
        bitmask |= (1 << (day - 1));
      }
      return bitmask;
    } catch (Exception e) {
      logger.error("Error while setting days in bitmask", e);
      throw new RuntimeException("Error while setting days in bitmask", e);
    }
  }

  /**
   * Retrieves the selected days from a bitmask.
   *
   * @param bitmask Bitmask representing the selected days.
   * @return Array of selected days.
   * @throws RuntimeException if there is an error while getting selected days from the bitmask
   */
  public static int[] getSelectedDays(int bitmask) {
    try {
      int[] selectedDays = new int[7];
      int index = 0;

      for (int i = 0; i < 7; i++) {
        int currentDay = 1 << i;
        if ((bitmask & currentDay) != 0) {
          selectedDays[index++] = i + 1;
        }
      }
      return Arrays.copyOf(selectedDays, index);
    } catch (Exception e) {
      logger.error("Error while getting selected days from bitmask", e);
      throw new RuntimeException("Error while getting selected days from bitmask", e);
    }
  }
}
