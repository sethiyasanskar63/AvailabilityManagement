package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing availability.
 * Author: Sanskar Sethiya
 */
public interface AvailabilityService {

  /**
   * Get a list of availability based on the provided parameters.
   *
   * @param availabilityId        ID of the availability (can be null for all availabilities)
   * @param accommodationTypeId   ID of the accommodation type (can be null for all types)
   * @param arrivalDate           Start date of the stay
   * @param departureDate         End date of the stay
   * @return List of availability
   */
  List<Availability> getAvailability(Long availabilityId, Long accommodationTypeId,
                                     LocalDate arrivalDate, LocalDate departureDate);

  /**
   * Save availability from DTO.
   *
   * @param availabilityDTO DTO containing availability information
   */
  void saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO);

  /**
   * Save all availabilities from a workbook.
   *
   * @param workbook Workbook containing availability data
   * @return Result message indicating success or failure
   */
  String saveAllAvailability(Workbook workbook);

  /**
   * Delete availability by its ID.
   *
   * @param id ID of the availability to be deleted
   * @return Result message indicating success or failure
   */
  String deleteAvailabilityById(Long id);
}
