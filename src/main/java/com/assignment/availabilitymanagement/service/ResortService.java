package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.ResortDTO;
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
   * @return A message indicating the outcome of the operation.
   */
  void deleteResortByID(Long id);
}
