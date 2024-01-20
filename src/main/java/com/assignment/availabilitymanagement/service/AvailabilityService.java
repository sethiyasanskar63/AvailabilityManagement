package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

  List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                     LocalDate arrivalDate, LocalDate departureDate);

  Availability saveAvailability(Availability availability);

  String deleteAvailabilityById(Long id);
}
