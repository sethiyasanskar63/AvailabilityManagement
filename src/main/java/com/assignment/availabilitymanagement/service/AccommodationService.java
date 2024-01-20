package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Accommodation;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationService {

  List<Accommodation> getAccommodations(Long accommodationId, LocalDate arrivalDate, LocalDate departureDate);

  Accommodation saveAccommodation(Accommodation accommodation);

  String deleteAccommodationById(Long id);
}
