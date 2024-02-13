package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the service layer for managing availabilities, offering methods to fetch,
 * save, and delete availabilities, as well as to import them from an Excel workbook.
 */
@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Autowired
  private AccommodationTypeRepository accommodationTypeRepository;

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
    Page<Availability> availabilities = availabilityRepository.findAll(spec, pageable);
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
    List<Availability> availabilities = availabilityRepository.findAll(spec);
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
  public AvailabilityDTO saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO) {
    logger.debug("Attempting to save new availability.");
    return checkForOverlapAndSave(availabilityDTO);
  }

  /**
   * Updates an availability from the provided DTO.
   *
   * @param availabilityDTO The updated availability DTO.
   * @return The updated availability DTO.
   */
  @Override
  @Transactional
  public AvailabilityDTO updateAvailabilityFromDTO(AvailabilityDTO availabilityDTO) {
    Availability updatedAvailability = availabilityRepository.saveAndFlush(availabilityMapper.toEntity(availabilityDTO));
    return availabilityMapper.toDto(updatedAvailability);
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
        checkForOverlapAndSave(availabilityDTO);
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
   * Checks for overlap with existing availabilities and saves the provided availability DTO.
   *
   * @param availabilityDTO The availability DTO to be saved.
   * @return The saved availability DTO.
   */
  private AvailabilityDTO checkForOverlapAndSave(AvailabilityDTO availabilityDTO) {
    AvailabilitySpecification spec = new AvailabilitySpecification(null, availabilityDTO.getAccommodationTypeId(), availabilityDTO.getStayFromDate(), availabilityDTO.getStayToDate());
    List<Availability> existingAvailabilities = availabilityRepository.findAll(spec);
    if (!existingAvailabilities.isEmpty()) {
      throw new IllegalStateException("Overlap detected: Availability already exists for the specified period.");
    }
    Availability availability = availabilityMapper.toEntity(availabilityDTO);
    AccommodationType accommodationType = accommodationTypeRepository.findById(availabilityDTO.getAccommodationTypeId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid Accommodation Type ID."));
    availability.setAccommodationType(accommodationType);
    Availability savedAvailability = availabilityRepository.save(availability);
    logger.debug("Successfully saved availability.");
    return availabilityMapper.toDto(savedAvailability);
  }
}
