package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
  private AvailabilityMapper availabilityMapper;

  @Autowired
  private WorkBookToAvailability workBookToAvailability;

  private final Sort sort = Sort.by(Sort.Order.asc("accommodationType.accommodationTypeId"), Sort.Order.asc("stayFromDate"));

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
    List<Availability> availabilities = availabilityRepository.findAll(spec,sort);
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
    try{
      AvailabilitySpecification spec = new AvailabilitySpecification(null, availabilityDTO.getAccommodationTypeId(), null, null);
      Sort sort = Sort.by(Sort.Order.asc("accommodationType.accommodationTypeId"), Sort.Order.asc("stayFromDate"));
      List<Availability> existingAvailabilities = availabilityRepository.findAll(spec,sort);
      List<Availability> updatedAvailabilities = updateAndSplitAvailabilities(availabilityMapper.toEntity(availabilityDTO),existingAvailabilities);
      availabilityRepository.saveAllAndFlush(updatedAvailabilities);
    } catch (Exception e){
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
        AvailabilitySpecification spec = new AvailabilitySpecification(null, availabilityDTO.getAccommodationTypeId(), null, null);
        List<Availability> existingAvailabilities = availabilityRepository.findAll(spec,sort);
        List<Availability> updatedAvailabilities = updateAndSplitAvailabilities(availabilityMapper.toEntity(availabilityDTO),existingAvailabilities);
        availabilityRepository.saveAllAndFlush(updatedAvailabilities);
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

  private List<Availability> updateAndSplitAvailabilities(Availability newAvailability, List<Availability> existingAvailabilities) {
    List<Availability> updatedAvailabilities = new ArrayList<>();
    LocalDate today = LocalDate.now();

    for (Availability existing : existingAvailabilities) {
      // Check if newAvailability's start date falls within an existing availability
      if (!newAvailability.getStayFromDate().isBefore(existing.getStayFromDate()) &&
          !newAvailability.getStayFromDate().isAfter(existing.getStayToDate())) {
        // Create new availability from the start of existing to the day before the start of new
        updatedAvailabilities.add(new Availability(null, existing.getStayFromDate(), newAvailability.getStayFromDate().minusDays(1),
            existing.getMinNight(), existing.getArrivalDays(), existing.getDepartureDays(), existing.getClosingDate(), existing.getAccommodationType()));

        // If newAvailability ends before existing ends, split existing into another new availability
        if (newAvailability.getStayToDate().isBefore(existing.getStayToDate())) {
          updatedAvailabilities.add(newAvailability);
          updatedAvailabilities.add(new Availability(null, newAvailability.getStayToDate().plusDays(1), existing.getStayToDate(),
              existing.getMinNight(), existing.getArrivalDays(), existing.getDepartureDays(), existing.getClosingDate(), existing.getAccommodationType()));
          existing.setClosingDate(today.atStartOfDay());
        } else {
          existing.setClosingDate(today.atStartOfDay());
        }
      } else {
        updatedAvailabilities.add(existing);
      }
    }

    // Handle the scenario of checking and linking availabilities post the end of newAvailability
    // This part would need additional logic to identify and adjust the subsequent availabilities (availability2 as per your example)

    // Add or merge the new availability appropriately
    // This requires additional logic to ensure no duplicate or overlapping entries are created inadvertently

    return mergeAndCloseAvailabilities(updatedAvailabilities, newAvailability);
  }

  private List<Availability> mergeAndCloseAvailabilities(List<Availability> availabilities, Availability newAvailability) {
    // Implement the logic to merge new availability with existing ones, adjust dates, and close as necessary
    // This would involve sorting, merging adjacent or overlapping periods, and potentially closing availabilities as described
    // For simplicity, this method stub needs detailed implementation based on your database and application logic

    return availabilities; // Placeholder return
  }
}
