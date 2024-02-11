package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
   * @return List of matching accommodation type DTOs
   */
  @Transactional(readOnly = true)
  List<AccommodationTypeDTO> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Save or update an accommodation type and return its DTO.
   *
   * @param accommodationTypeDTO DTO of the accommodation type to be saved or updated
   * @return DTO of the saved or updated accommodation type
   */
  @Transactional
  AccommodationTypeDTO saveAccommodationType(AccommodationTypeDTO accommodationTypeDTO);

  /**
   * Delete an accommodation type by ID.
   *
   * @param id ID of the accommodation type to be deleted
   */
  @Transactional
  void deleteAccommodationTypeById(Long id);
}
