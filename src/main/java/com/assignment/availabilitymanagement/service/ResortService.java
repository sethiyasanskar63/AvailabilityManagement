package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Resort;

import java.util.List;

/**
 * Service interface for managing resorts.
 * Author: Sanskar Sethiya
 */
public interface ResortService {

  /**
   * Get a list of resorts based on the provided resortId.
   *
   * @param resortId ID of the resort (can be null for all resorts)
   * @return List of resorts
   */
  List<Resort> getResorts(Long resortId);

  /**
   * Save or update a resort.
   *
   * @param resort Resort to be saved or updated
   * @return Saved or updated resort
   */
  Resort saveResort(Resort resort);

  /**
   * Delete a resort by its ID.
   *
   * @param id ID of the resort to be deleted
   * @return Result message indicating success or failure
   */
  String deleteResortByID(Long id);
}
