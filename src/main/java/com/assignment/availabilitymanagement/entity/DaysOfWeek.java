package com.assignment.availabilitymanagement.entity;

import java.util.Arrays;

public class DaysOfWeek {
  public static final int MONDAY = 1;
  public static final int TUESDAY = 2;
  public static final int WEDNESDAY = 3;
  public static final int THURSDAY = 4;
  public static final int FRIDAY = 5;
  public static final int SATURDAY = 6;
  public static final int SUNDAY = 7;

  public static int setDays(int... selectedDays) {
    int bitmask = 0;
    for (int day : selectedDays) {
      bitmask |= (1 << (day - 1));
    }
    return bitmask;
  }

  public static int[] getSelectedDays(int bitmask) {
    int[] selectedDays = new int[7];
    int index = 0;

    for (int i = 0; i < 7; i++) {
      int currentDay = 1 << i;
      if ((bitmask & currentDay) != 0) {
        selectedDays[index++] = i + 1;
      }
    }
    return Arrays.copyOf(selectedDays, index);
  }
}