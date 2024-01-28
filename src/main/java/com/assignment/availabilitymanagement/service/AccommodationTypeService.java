package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.AccommodationType;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing accommodation types.
 * Author: Sanskar Sethiya
 */
public interface AccommodationTypeService {

  /**
   * Retrieve accommodation types based on the specified criteria.
   *
   * @param accommodationTypeId ID of the accommodation type (can be null)
   * @param arrivalDate         Arrival date criteria (can be null)
   * @param departureDate       Departure date criteria (can be null)
   * @return List of matching accommodation types
   */
  List<AccommodationType> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Save or update an accommodation type.
   *
   * @param accommodationType Accommodation type to be saved or updated
   * @return Saved or updated accommodation type
   */
  AccommodationType saveAccommodationType(AccommodationType accommodationType);

  /**
   * Delete an accommodation type by ID.
   *
   * @param id ID of the accommodation type to be deleted
   * @return Success message or error information
   */
  String deleteAccommodationTypeById(Long id);
}
