package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service interface for managing resorts. Provides methods for retrieving, saving,
 * and deleting resorts using Data Transfer Objects (DTOs).
 */
public interface ResortService {

  /**
   * Retrieves a list of resorts based on the provided resort ID. If the resort ID is null,
   * retrieves all resorts.
   *
   * @param resortId Optional ID of the resort for filtering.
   * @return A list of ResortDTO objects representing the resorts.
   */
  List<ResortDTO> getResorts(Long resortId);

  /**
   * Retrieves a paginated list of resorts based on the provided resort ID. If the resort ID is null,
   * retrieves all resorts in a paginated format. This method supports pagination, allowing clients to
   * request a subset of resorts based on page number and size, along with optional sorting criteria.
   *
   * @param resortId Optional ID of the resort for filtering. If provided, the method returns resorts
   *                 matching the given ID. If null, all resorts are considered for pagination.
   * @param pageable A {@link Pageable} object containing pagination and sorting information. Determines
   *                 the size of the page, the current page number, and sorting parameters.
   * @return A {@link Page} of {@link ResortDTO} objects representing the resorts within the specified
   *         page. Includes pagination information such as total number of pages, total resorts, current
   *         page number, and the size of the page.
   */
  @Transactional(readOnly = true)
  Page<ResortDTO> getResorts(Long resortId, Pageable pageable);

  /**
   * Saves or updates a resort based on the provided ResortDTO. This method is responsible
   * for both creating new resorts and updating existing ones.
   *
   * @param resortDTO The ResortDTO object containing resort data to be saved or updated.
   * @return The saved or updated ResortDTO object.
   */
  ResortDTO saveResort(ResortDTO resortDTO);

  /**
   * Deletes a resort identified by its ID. If the resort does not exist, this method
   * is expected to handle the situation appropriately, typically by logging the incident
   * or throwing a managed exception.
   *
   * @param id The ID of the resort to be deleted.
   */
  void deleteResortByID(Long id);
}
