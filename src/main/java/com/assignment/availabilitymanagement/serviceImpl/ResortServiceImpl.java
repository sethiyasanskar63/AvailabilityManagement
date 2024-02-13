package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.mapper.ResortMapper;
import com.assignment.availabilitymanagement.repository.ResortRepository;
import com.assignment.availabilitymanagement.service.ResortService;
import com.assignment.availabilitymanagement.specification.ResortSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service implementation for managing resorts.
 */
@Service
public class ResortServiceImpl implements ResortService {

  private static final Logger logger = LoggerFactory.getLogger(ResortServiceImpl.class);

  @Autowired
  private ResortRepository resortRepository;

  @Autowired
  private ResortMapper resortMapper;

  /**
   * Retrieves a list of resorts, optionally filtered by a resort ID.
   *
   * @param resortId Optional resort ID for filtering.
   * @return A list of {@link ResortDTO} matching the criteria.
   */
  @Override
  @Transactional(readOnly = true)
  public List<ResortDTO> getResorts(Long resortId) {
    List<Resort> resorts =  resortRepository.findAll(ResortSpecification.hasResortId(resortId));
    logger.debug("Retrieved {} resorts.", resorts.size());
    return resorts.stream().map(resortMapper::toDto).collect(Collectors.toList());
  }

  /**
   * Retrieves a paginated list of resorts, optionally filtered by a resort ID. This method supports pagination, allowing for efficient data retrieval
   * in scenarios where the total number of resorts is large. The resort ID parameter is optional and serves as a filter to narrow down the search results.
   *
   * @param resortId Optional. The ID of the specific resort to retrieve.
   * @param pageable The pagination information including page number, size, and sorting criteria.
   * @return A {@link Page} of {@link ResortDTO} objects matching the criteria, providing a subset of resorts according to the pagination settings.
   */
  @Override
  @Transactional(readOnly = true)
  public Page<ResortDTO> getResorts(Long resortId, Pageable pageable) {
    logger.debug("Fetching paginated list of resorts with optional filtering by resort ID.");
    Page<Resort> resorts = resortRepository.findAll(ResortSpecification.hasResortId(resortId), pageable);
    return resorts.map(resortMapper::toDto);
  }

  /**
   * Saves a new or updates an existing resort.
   *
   * @param resortDTO The resort data to save.
   * @return The saved {@link ResortDTO}.
   */
  @Override
  @Transactional
  public ResortDTO saveResort(ResortDTO resortDTO) {
    Resort resort = resortMapper.toEntity(resortDTO);
    Resort savedResort = resortRepository.save(resort);
    logger.debug("Saved resort with ID: {}", savedResort.getResortId());
    return resortMapper.toDto(savedResort);
  }

  /**
   * Deletes a resort by its ID.
   *
   * @param id The ID of the resort to delete.
   * @throws NoSuchElementException if the resort does not exist.
   */
  @Override
  @Transactional
  public void deleteResortByID(Long id) {
    try {
      resortRepository.deleteById(id);
      logger.debug("Deleted resort with ID: {}", id);
    } catch (EmptyResultDataAccessException e) {
      logger.error("Attempted to delete a non-existent resort with ID: {}", id);
      throw new NoSuchElementException("Resort not found with ID: " + id);
    }
  }
}
