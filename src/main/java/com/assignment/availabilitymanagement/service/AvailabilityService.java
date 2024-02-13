package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing availabilities.
 * Provides methods to fetch, save, and delete availability records.
 */
public interface AvailabilityService {

  /**
   * Fetches a list of all availabilities without applying pagination. This method retrieves availabilities based on optional filtering criteria.
   * If no parameters are provided, all availabilities are returned. Use this method with caution to avoid performance issues with large datasets.
   *
   * @param availabilityId      Optional. The specific ID of the availability to retrieve. If provided, filters the results to include only the availability with this ID.
   * @param accommodationTypeId Optional. The ID of the accommodation type to filter availabilities. If provided, only availabilities associated with this accommodation type are included.
   * @param arrivalDate         Optional. The start date of the availability period for filtering. If provided, only availabilities starting on or after this date are included.
   * @param departureDate       Optional. The end date of the availability period for filtering. If provided, only availabilities ending on or before this date are included.
   * @return A list of {@link AvailabilityDTO} objects that match the provided criteria.
   */
  List<AvailabilityDTO> getAvailability(Long availabilityId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Fetches a page of availabilities based on optional filtering criteria and pagination information. This method is useful for large datasets where
   * retrieving all records at once could impact performance. Each parameter is optional and used to filter the results.
   *
   * @param availabilityId      Optional. The specific ID of the availability to retrieve. If provided, filters the results to include only the availability with this ID.
   * @param accommodationTypeId Optional. The ID of the accommodation type to filter availabilities. If provided, only availabilities associated with this accommodation type are included.
   * @param arrivalDate         Optional. The start date of the availability period for filtering. If provided, only availabilities starting on or after this date are included.
   * @param departureDate       Optional. The end date of the availability period for filtering. If provided, only availabilities ending on or before this date are included.
   * @param pageable            The {@link Pageable} instance containing pagination information such as page number, page size, and sorting criteria.
   * @return A {@link Page} of {@link AvailabilityDTO} objects that match the provided criteria. This object contains the requested page of availabilities, along with additional pagination information.
   */
  Page<AvailabilityDTO> getAvailability(Long availabilityId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate, Pageable pageable);

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
   */
  void deleteAvailabilityById(Long id);
}
