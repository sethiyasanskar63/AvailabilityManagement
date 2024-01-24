package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

  List<Availability> getAvailability(Long availabilityId, Long accommodationTypeId,
                                     LocalDate arrivalDate, LocalDate departureDate);

  void saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO);

  String saveAllAvailability(Workbook workbook);

  String deleteAvailabilityById(Long id);
}
