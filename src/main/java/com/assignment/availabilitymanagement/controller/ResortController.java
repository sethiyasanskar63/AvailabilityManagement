package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import com.assignment.availabilitymanagement.service.ResortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Handles resort-related operations, offering endpoints for CRUD operations on resorts.
 */
@RestController
@RequestMapping("/api/resorts")
public class ResortController {

  private static final Logger logger = LoggerFactory.getLogger(ResortController.class);

  @Autowired
  private ResortService resortService;

  /**
   * Retrieves resorts, optionally filtered by resort ID.
   *
   * @param resortId Optional ID to filter the resort.
   * @return ResponseEntity with List of ResortDTOs or a specific resort if ID is provided, including not found or bad request errors.
   */
  @GetMapping
  public ResponseEntity<?> getResorts(@RequestParam(required = false) Long resortId) {
    try {
      List<ResortDTO> resorts = resortService.getResorts(resortId);
      if (resorts.isEmpty() && resortId != null) {
        String message = String.format("No resort found with ID: %d", resortId);
        logger.debug(message);
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(resorts);
    } catch (Exception e) {
      logger.error("Error retrieving resorts: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving resorts due to an internal error.");
    }
  }

  /**
   * Adds a new resort.
   *
   * @param resortDTO DTO containing resort details.
   * @return ResponseEntity with created ResortDTO or error message in case of failure.
   */
  @PostMapping
  public ResponseEntity<?> addResort(@Valid @RequestBody ResortDTO resortDTO) {
    try {
      ResortDTO savedResort = resortService.saveResort(resortDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedResort);
    } catch (Exception e) {
      logger.error("Error saving resort: {}", e.getMessage());
      return ResponseEntity.badRequest().body(String.format("Error saving resort: %s", e.getMessage()));
    }
  }

  /**
   * Updates an existing resort by ID.
   *
   * @param id The ID of the resort to update.
   * @param resortDTO DTO with updated resort details.
   * @return ResponseEntity with updated ResortDTO or error message if resort doesn't exist or in case of failure.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateResort(@PathVariable Long id, @Valid @RequestBody ResortDTO resortDTO) {
    try {
      resortDTO.setResortId(id);
      ResortDTO updatedResort = resortService.saveResort(resortDTO);
      return ResponseEntity.ok(updatedResort);
    } catch (EmptyResultDataAccessException e) {
      String message = String.format("Cannot update non-existent resort with ID: %d", id);
      logger.debug(message);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      logger.error("Error updating resort with ID {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body(String.format("Error updating resort: %s", e.getMessage()));
    }
  }

  /**
   * Deletes a resort by its ID.
   *
   * @param id ID of the resort to delete.
   * @return ResponseEntity with success message or error message if resort doesn't exist or in case of failure.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteResortByID(@PathVariable Long id) {
    try {
      resortService.deleteResortByID(id);
      return ResponseEntity.ok().body(String.format("Resort with ID: %d was successfully deleted.", id));
    } catch (EmptyResultDataAccessException e) {
      String message = String.format("Attempted to delete a non-existent resort with ID: %d", id);
      logger.debug(message);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      logger.error("Error deleting resort with ID {}: {}", id, e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error deleting resort: %s", e.getMessage()));
    }
  }
}
