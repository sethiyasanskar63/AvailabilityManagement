package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Accommodation;

import java.util.List;

public interface AccommodationService {

  List<Accommodation> getAllAccommodations();

  Accommodation getAccommodationById(Long id);

  Accommodation saveAccommodation(Accommodation accommodation);

  String deleteAccommodationById(Long id);
}
