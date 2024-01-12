package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.AccommodationType;

import java.util.List;

public interface AccommodationTypeService {

  List<AccommodationType> getAllAccommodationTypes();

  AccommodationType getAccommodationTypeById(Long id);

  AccommodationType saveAccommodationType(AccommodationType accommodationType);

  String deleteAccommodationTypeById(Long id);
}
