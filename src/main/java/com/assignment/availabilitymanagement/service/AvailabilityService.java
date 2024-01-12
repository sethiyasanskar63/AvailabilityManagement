package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Availability;

import java.util.List;

public interface AvailabilityService {

  List<Availability> getAllAvailability();

  Availability getAvailabilityById(Long id);

  Availability saveAvailability(Availability availability);

  String deleteAvailabilityById(Long id);
}
