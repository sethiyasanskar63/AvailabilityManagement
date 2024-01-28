package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.repository.ResortRepository;
import com.assignment.availabilitymanagement.service.ResortService;
import com.assignment.availabilitymanagement.specification.ResortSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing resorts.
 * Author: Sanskar Sethiya
 */
@Service
public class ResortServiceImpl implements ResortService {

  private static final Logger logger = LoggerFactory.getLogger(ResortServiceImpl.class);

  @Autowired
  private ResortRepository resortRepository;

  /**
   * Get resorts based on the specified parameters.
   *
   * @param resortId ID of the resort
   * @return List of resorts
   * @throws RuntimeException if there is an error while fetching data from the database
   */
  @Override
  public List<Resort> getResorts(Long resortId) {
    try {
      ResortSpecification resortSpecification = new ResortSpecification(resortId);
      return resortRepository.findAll(resortSpecification);
    } catch (Exception e) {
      logger.error("Error while getting resorts", e);
      throw new RuntimeException("Error while getting resorts", e);
    }
  }

  /**
   * Save a resort.
   *
   * @param resort Resort to be saved
   * @return Saved resort
   * @throws RuntimeException if there is an error while saving data to the database
   */
  @Override
  public Resort saveResort(Resort resort) {
    try {
      return resortRepository.saveAndFlush(resort);
    } catch (Exception e) {
      logger.error("Error while saving resort", e);
      throw new RuntimeException("Error while saving resort", e);
    }
  }

  /**
   * Delete resort by ID.
   *
   * @param id ID of the resort to be deleted
   * @return Success message
   * @throws RuntimeException if there is an error while deleting data from the database
   */
  @Override
  public String deleteResortByID(Long id) {
    try {
      resortRepository.deleteById(id);
      return "Deleted resort ID: " + id;
    } catch (Exception e) {
      logger.error("Error while deleting resort", e);
      throw new RuntimeException("Error while deleting resort", e);
    }
  }
}
