package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing accommodation types within the application.
 * Provides endpoints for retrieving, adding, updating, and deleting accommodation types.
 */
@RestController
@RequestMapping("/api/accommodationTypes")
public class AccommodationTypeController {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeController.class);

  @Autowired
  private AccommodationTypeService accommodationTypeService;

  /**
   * Retrieves a paginated list of all accommodation types, optionally filtered by accommodation type ID or availability within a date range.
   *
   * @param accommodationTypeId Optional ID of the accommodation type for filtering.
   * @param arrivalDate         Optional arrival date for availability checking.
   * @param departureDate       Optional departure date for availability checking.
   * @param pageable            Pagination information including page number, page size, and sorting criteria.
   * @return ResponseEntity containing the paginated list of accommodation types or an appropriate error message.
   */
  @GetMapping
  public ResponseEntity<?> getAccommodationTypes(
      @RequestParam(required = false) Long accommodationTypeId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
      Pageable pageable) {
    try {
      if (accommodationTypeId != null && (arrivalDate != null || departureDate != null)) {
        return ResponseEntity.badRequest().body("Cannot specify accommodationTypeId together with arrivalDate or departureDate.");
      }

      if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
        logger.debug("Both arrival and departure dates are required to check availability.");
        return ResponseEntity.badRequest().body("Both arrival and departure dates are required to check availability.");
      }

      Page<AccommodationTypeDTO> accommodationTypesPage = accommodationTypeService.getAccommodationTypes(accommodationTypeId, arrivalDate, departureDate, pageable);
      if (accommodationTypesPage.getContent().isEmpty()) {
        logger.debug("No matching accommodation types found.");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(accommodationTypesPage);
    } catch (Exception e) {
      logger.error("Error retrieving available accommodation types: {}", e.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error occurred while processing your request.");
    }
  }

  /**
   * Adds a new accommodation type.
   *
   * @param accommodationTypeDTO DTO containing the details of the accommodation type to be added.
   * @return ResponseEntity with the added AccommodationTypeDTO or an error message.
   */
  @PostMapping
  public ResponseEntity<?> addAccommodationType(@Valid @RequestBody AccommodationTypeDTO accommodationTypeDTO) {
    try {
      AccommodationTypeDTO savedAccommodationType = accommodationTypeService.saveAccommodationType(accommodationTypeDTO);
      return ResponseEntity.ok(savedAccommodationType);
    } catch (Exception e) {
      logger.error("Error saving accommodation type: {}", e.getMessage());
      return ResponseEntity.badRequest().body("Error saving accommodation type: " + e.getMessage());
    }
  }

  /**
   * Updates an existing accommodation type by ID.
   *
   * @param id                   The ID of the accommodation type to update.
   * @param accommodationTypeDTO DTO with updated accommodation type details.
   * @return ResponseEntity with the updated AccommodationTypeDTO or an error message.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateAccommodationType(@PathVariable Long id, @Valid @RequestBody AccommodationTypeDTO accommodationTypeDTO) {
    try {
      accommodationTypeDTO.setAccommodationTypeId(id);
      AccommodationTypeDTO updatedAccommodationType = accommodationTypeService.saveAccommodationType(accommodationTypeDTO);
      return ResponseEntity.ok(updatedAccommodationType);
    } catch (Exception e) {
      logger.error("Error updating accommodation type with ID {}: {}", id, e.getMessage());
      return ResponseEntity.badRequest().body("Error updating accommodation type: " + e.getMessage());
    }
  }

  /**
   * Deletes an accommodation type by its ID.
   *
   * @param id The ID of the accommodation type to be deleted.
   * @return ResponseEntity with a success message or an error message if the accommodation type does not exist or in case of failure.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAccommodationTypeById(@PathVariable Long id) {
    try {
      accommodationTypeService.deleteAccommodationTypeById(id);
      logger.info("Successfully deleted accommodation type with ID: {}", id);
      return ResponseEntity.ok("Accommodation type with ID: " + id + " was successfully deleted.");
    } catch (RuntimeException e) {
      logger.error("Attempted to delete a non-existent accommodation type with ID: {}", id);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      logger.error("Error deleting accommodation type with ID {}: {}", id, e.getMessage());
      return ResponseEntity.internalServerError().body("Error deleting accommodation type: " + e.getMessage());
    }
  }
}
