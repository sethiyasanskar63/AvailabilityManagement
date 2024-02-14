package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import com.assignment.availabilitymanagement.util.WorkBookToAvailability;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the service layer for managing availabilities, offering methods to fetch,
 * save, and delete availabilities, as well as to import them from an Excel workbook.
 */
@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilityServiceImpl.class);
  private final Sort sort = Sort.by(Sort.Order.asc("accommodationType.accommodationTypeId"), Sort.Order.asc("stayFromDate"));
  @Autowired
  private AvailabilityRepository availabilityRepository;
  @Autowired
  private AvailabilityMapper availabilityMapper;
  @Autowired
  private WorkBookToAvailability workBookToAvailability;

  /**
   * Fetches availabilities based on the specified criteria.
   *
   * @param availabilityId      The ID of the availability.
   * @param accommodationTypeId The ID of the accommodation type.
   * @param arrivalDate         The arrival date.
   * @param departureDate       The departure date.
   * @param pageable            The pageable object for pagination.
   * @return A page of availability DTOs.
   */
  @Override
  @Transactional(readOnly = true)
  public Page<AvailabilityDTO> getAvailability(Long availabilityId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate, Pageable pageable) {
    logger.debug("Fetching availability with specified criteria.");
    AvailabilitySpecification spec = new AvailabilitySpecification(availabilityId, accommodationTypeId, arrivalDate, departureDate);
    Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    Page<Availability> availabilities = availabilityRepository.findAll(spec, pageableWithSort);
    return availabilities.map(availabilityMapper::toDto);
  }

  /**
   * Fetches availabilities based on the specified criteria.
   *
   * @param availabilityId      The ID of the availability.
   * @param accommodationTypeId The ID of the accommodation type.
   * @param arrivalDate         The arrival date.
   * @param departureDate       The departure date.
   * @return A list of availability DTOs.
   */
  @Override
  @Transactional(readOnly = true)
  public List<AvailabilityDTO> getAvailability(Long availabilityId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    logger.debug("Fetching availability with specified criteria.");
    AvailabilitySpecification spec = new AvailabilitySpecification(availabilityId, accommodationTypeId, arrivalDate, departureDate);
    List<Availability> availabilities = availabilityRepository.findAll(spec, sort);
    return availabilities.stream().map(availabilityMapper::toDto).collect(Collectors.toList());
  }

  /**
   * Saves an availability from the provided DTO.
   *
   * @param availabilityDTO The availability DTO to be saved.
   * @return The saved availability DTO.
   */
  @Override
  @Transactional
  public String saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO) {
    logger.debug("Attempting to save new availability.");
    try {
      availabilityRepository.saveAllAndFlush(updateAndSplitAvailabilities(availabilityMapper.toEntity(availabilityDTO)));
    } catch (Exception e) {
      logger.error("Error saving availability {}", availabilityDTO, e);
      throw new IllegalStateException("Failed to delete availability: " + e.getMessage());
    }
    return "Availability Saved";
  }

  /**
   * Saves all availabilities from the provided Workbook.
   *
   * @param workbook The Workbook containing availabilities.
   * @return A message indicating the completion of the batch import.
   */
  @Override
  @Transactional
  public String saveAllAvailability(Workbook workbook) {
    logger.debug("Starting batch import of availabilities from workbook.");
    List<AvailabilityDTO> availabilitiesDTO = workBookToAvailability.excelToAvailabilityDTO(workbook);
    for (AvailabilityDTO availabilityDTO : availabilitiesDTO) {
      try {
        availabilityRepository.saveAllAndFlush(updateAndSplitAvailabilities(availabilityMapper.toEntity(availabilityDTO)));
      } catch (IllegalStateException e) {
        logger.warn(e.getMessage());
      }
    }
    return "Batch import completed.";
  }

  /**
   * Deletes an availability by its ID.
   *
   * @param id The ID of the availability to be deleted.
   */
  @Override
  @Transactional
  public void deleteAvailabilityById(Long id) {
    try {
      availabilityRepository.deleteById(id);
      logger.debug("Deleted availability with ID: {}", id);
    } catch (DataIntegrityViolationException e) {
      logger.error("Error deleting availability: Integrity violation for ID: {}", id, e);
      throw new IllegalStateException("Cannot delete availability, as it is referenced by other records.");
    } catch (Exception e) {
      logger.error("Error deleting availability with ID: {}", id, e);
      throw new IllegalStateException("Failed to delete availability: " + e.getMessage());
    }
  }

  /**
   * Updates and splits availabilities based on a new availability input. This method first checks for
   * existing availabilities that overlap with the new availability. If no overlapping availabilities are found,
   * the new availability is simply added. For overlapping cases, it adjusts the existing availabilities by closing
   * them and potentially adding adjusted new availability periods before or after the overlap, depending on the
   * specific overlap conditions.
   *
   * @param newAvailability The new availability to add or merge into the existing availabilities.
   * @return A list of availabilities that have been updated to include the new availability, with necessary adjustments
   * made to existing availabilities that overlap with the new availability period.
   */
  private List<Availability> updateAndSplitAvailabilities(Availability newAvailability) {
    AvailabilitySpecification spec = new AvailabilitySpecification(null, newAvailability.getAccommodationType().getAccommodationTypeId(),
        newAvailability.getStayFromDate(), newAvailability.getStayToDate());
    List<Availability> existingAvailabilities = availabilityRepository.findAll(spec);
    List<Availability> updatedAvailabilities = new ArrayList<>();

    // Handle the case where no existing availabilities overlap with the new one
    if (existingAvailabilities.isEmpty()) {
      updatedAvailabilities.add(newAvailability);
      return updatedAvailabilities;
    }

    // Adjust existing availabilities based on the new availability's period
    // New availability starts before or on the same day as existing availability
    if (newAvailability.getStayFromDate().isBefore(existingAvailabilities.get(0).getStayFromDate()) ||
        newAvailability.getStayFromDate().isEqual(existingAvailabilities.get(0).getStayFromDate())) {
      updatedAvailabilities.add(new Availability(null, newAvailability.getStayFromDate(), newAvailability.getStayToDate(),
          newAvailability.getMinNight(), newAvailability.getArrivalDays(),
          newAvailability.getDepartureDays(), null, newAvailability.getAccommodationType()));
    }

    // New availability starts after existing availability
    if (newAvailability.getStayFromDate().isAfter(existingAvailabilities.get(0).getStayFromDate())) {
      updatedAvailabilities.add(new Availability(null, existingAvailabilities.get(0).getStayFromDate(),
          newAvailability.getStayFromDate().minusDays(1), existingAvailabilities.get(0).getMinNight(),
          existingAvailabilities.get(0).getArrivalDays(), existingAvailabilities.get(0).getDepartureDays(),
          null, existingAvailabilities.get(0).getAccommodationType()));
    }

    // New availability ends before the last existing availability
    if (newAvailability.getStayToDate().isBefore(existingAvailabilities.get(existingAvailabilities.size() - 1).getStayToDate())) {
      updatedAvailabilities.add(new Availability(null, newAvailability.getStayToDate().plusDays(1),
          existingAvailabilities.get(existingAvailabilities.size() - 1).getStayToDate(),
          existingAvailabilities.get(existingAvailabilities.size() - 1).getMinNight(),
          existingAvailabilities.get(existingAvailabilities.size() - 1).getArrivalDays(),
          existingAvailabilities.get(existingAvailabilities.size() - 1).getDepartureDays(),
          null, existingAvailabilities.get(existingAvailabilities.size() - 1).getAccommodationType()));
    }

    // Close all existing overlapping availabilities
    for (Availability existingAvailability : existingAvailabilities) {
      existingAvailability.setClosingDate(LocalDateTime.now());
    }
    updatedAvailabilities.addAll(existingAvailabilities);

    return updatedAvailabilities;
  }
}
