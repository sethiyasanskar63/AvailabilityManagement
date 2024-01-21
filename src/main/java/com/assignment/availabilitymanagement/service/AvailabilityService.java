package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Availability;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

  List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                     LocalDate arrivalDate, LocalDate departureDate);

  Availability saveAvailability(Availability availability);

  String saveAllAvailabilityFromWorkbook(Workbook workbook);

  String deleteAvailabilityById(Long id);
}
