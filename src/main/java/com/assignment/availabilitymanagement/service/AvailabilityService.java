package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing availabilities.
 * Provides methods to fetch, save, and delete availability records.
 */
public interface AvailabilityService {

  /**
   * Retrieves a list of availabilities based on the provided criteria. Each parameter is optional and used to filter the results.
   *
   * @param availabilityId Optional ID of the specific availability.
   * @param accommodationTypeId Optional ID of the accommodation type for filtering.
   * @param arrivalDate Optional start date for the availability period.
   * @param departureDate Optional end date for the availability period.
   * @return A list of {@link AvailabilityDTO} matching the criteria.
   */
  List<AvailabilityDTO> getAvailability(Long availabilityId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Saves an availability record from the provided AvailabilityDTO.
   *
   * @param availabilityDTO DTO containing the availability information to be saved.
   */
  AvailabilityDTO saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO);

  @Transactional
  AvailabilityDTO updateAvailabilityFromDTO(AvailabilityDTO availabilityDTO);

  /**
   * Saves all availabilities contained within a provided Excel workbook.
   * Each row in the workbook is expected to represent a single availability record.
   *
   * @param workbook Excel workbook containing availability data.
   * @return String message indicating the result of the operation, either success or an error message.
   */
  String saveAllAvailability(Workbook workbook);

  /**
   * Deletes an availability record by its ID.
   *
   * @param id The ID of the availability to be deleted.
   * @return String message indicating the result of the deletion operation, either success or an error message.
   */
  String deleteAvailabilityById(Long id);
}
