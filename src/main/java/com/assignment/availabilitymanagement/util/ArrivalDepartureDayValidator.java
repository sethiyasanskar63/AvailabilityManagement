package com.assignment.availabilitymanagement.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ArrivalDepartureDayValidator {

  public boolean canArrive(Date stayFromDate, String arrivalDays) {
    return Arrays.asList(arrivalDays.split(",")).contains(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
  }
}
