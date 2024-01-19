package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.AccommodationType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AccommodationTypeService {

  List<AccommodationType> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  AccommodationType saveAccommodationType(AccommodationType accommodationType);

  String deleteAccommodationTypeById(Long id);
}
