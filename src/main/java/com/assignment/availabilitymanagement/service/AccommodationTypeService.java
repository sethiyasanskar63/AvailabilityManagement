package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
   * @return List of matching accommodation type DTOs
   */
  @Transactional(readOnly = true)
  List<AccommodationTypeDTO> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Retrieves a paginated list of accommodation types based on the provided criteria.
   * Each parameter is optional and used to filter the results. The method supports pagination
   * through the {@link Pageable} parameter, allowing clients to specify the page number, page size,
   * and sorting criteria.
   *
   * @param accommodationTypeId Optional ID for filtering by a specific accommodation type.
   * @param arrivalDate         Optional date for filtering accommodation types available after this date.
   * @param departureDate       Optional date for filtering accommodation types available before this date.
   * @param pageable            {@link Pageable} object containing pagination and sorting criteria.
   * @return A {@link Page} of {@link AccommodationTypeDTO} that matches the criteria. The page contains
   * accommodation types according to the pagination and sorting criteria specified in the {@code pageable} parameter.
   */
  @Transactional(readOnly = true)
  Page<AccommodationTypeDTO> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate, Pageable pageable);

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
