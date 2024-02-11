package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.mapper.AccommodationTypeMapper;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import com.assignment.availabilitymanagement.specification.AccommodationTypeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing accommodation types.
 */
@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeServiceImpl.class);

  @Autowired
  private AccommodationTypeRepository accommodationTypeRepository;

  @Autowired
  private AccommodationTypeMapper accommodationTypeMapper;

  /**
   * Retrieves accommodation types based on the given criteria.
   *
   * @param accommodationTypeId Optional ID for filtering a specific accommodation type.
   * @param arrivalDate Optional start date for availability filtering.
   * @param departureDate Optional end date for availability filtering.
   * @return A list of {@link AccommodationTypeDTO} that match the criteria.
   */
  @Override
  @Transactional(readOnly = true)
  public List<AccommodationTypeDTO> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    AccommodationTypeSpecification specification = new AccommodationTypeSpecification(accommodationTypeId, arrivalDate, departureDate);
    List<AccommodationType> accommodationTypes = accommodationTypeRepository.findAll(specification);
    logger.debug("Retrieved {} accommodation types based on the given criteria.", accommodationTypes.size());
    return accommodationTypes.stream()
        .map(accommodationTypeMapper::entityToDto)
        .collect(Collectors.toList());
  }

  /**
   * Saves a new or updates an existing accommodation type.
   *
   * @param accommodationTypeDTO The accommodation type data to save.
   * @return The saved accommodation type as {@link AccommodationTypeDTO}.
   */
  @Override
  @Transactional
  public AccommodationTypeDTO saveAccommodationType(AccommodationTypeDTO accommodationTypeDTO) {
    try {
      AccommodationType accommodationType = accommodationTypeMapper.dtoToEntity(accommodationTypeDTO);
      AccommodationType savedAccommodationType = accommodationTypeRepository.save(accommodationType);
      logger.debug("Saved accommodation type with ID: {}", savedAccommodationType.getAccommodationTypeId());
      return accommodationTypeMapper.entityToDto(savedAccommodationType);
    } catch (Exception e) {
      logger.error("Error saving accommodation type: ", e);
      throw new RuntimeException("Failed to save accommodation type: " + e.getMessage(), e);
    }
  }

  /**
   * Deletes an accommodation type by its ID.
   *
   * @param id The ID of the accommodation type to delete.
   * @throws RuntimeException if the accommodation type does not exist.
   */
  @Override
  @Transactional
  public void deleteAccommodationTypeById(Long id) {
    try {
      accommodationTypeRepository.deleteById(id);
      logger.debug("Deleted accommodation type with ID: {}", id);
    } catch (EmptyResultDataAccessException e) {
      logger.error("Attempted to delete a non-existent accommodation type with ID: {}", id);
      throw new RuntimeException("Accommodation type not found with ID: " + id);
    }
  }
}
