package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Availability;

import java.util.Date;
import java.util.List;

public interface AvailabilityService {

  List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                     Integer minNights, String arrivalDays, String departureDays,
                                     Date stayFromDate, Date stayToDate);

  Availability saveAvailability(Availability availability);

  String deleteAvailabilityById(Long id);
}
