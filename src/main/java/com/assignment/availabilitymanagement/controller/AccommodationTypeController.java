package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing Accommodation Types.
 * Author: Sanskar Sethiya
 */
@RestController
@RequestMapping("/accommodationType")
public class AccommodationTypeController {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeController.class);

  @Autowired
  AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  /**
   * Retrieves a list of Accommodation Types based on the provided parameters.
   *
   * @param accommodationTypeId The ID of the Accommodation Type (optional).
   * @param arrivalDate         The arrival date (optional).
   * @param departureDate       The departure date (optional).
   * @return ResponseEntity with a list of AccommodationTypeDTO.
   */
  @GetMapping("/getAccommodationTypes")
  public ResponseEntity<List<AccommodationTypeDTO>> getAccommodationTypes(
      @RequestParam(name = "accommodationTypeId", required = false) Long accommodationTypeId,
      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate) {

    try {
      if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
        logger.warn("Invalid request parameters for getAccommodationTypes");
        return ResponseEntity.badRequest().build();
      }

      List<AccommodationTypeDTO> accommodationTypes = accommodationTypeServiceImpl.getAccommodationTypes(accommodationTypeId, arrivalDate, departureDate)
          .stream().map(AccommodationTypeDTO::new).collect(Collectors.toList());

      logger.info("Successfully retrieved accommodation types");
      return ResponseEntity.ok(accommodationTypes);
    } catch (Exception e) {
      logger.error("Error while processing getAccommodationTypes request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Adds a new Accommodation Type.
   *
   * @param accommodationType The Accommodation Type object to be added.
   * @return ResponseEntity with the added AccommodationTypeDTO.
   */
  @PostMapping("/addAccommodationType")
  public ResponseEntity<AccommodationTypeDTO> addAccommodationType(@RequestBody AccommodationType accommodationType) {
    try {
      AccommodationTypeDTO addedAccommodationType = new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
      logger.info("Successfully added accommodation type with ID: {}", addedAccommodationType.getAccommodationTypeId());
      return ResponseEntity.ok(addedAccommodationType);
    } catch (Exception e) {
      logger.error("Error while processing addAccommodationType request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Updates an existing Accommodation Type.
   *
   * @param accommodationType The Accommodation Type object to be updated.
   * @return ResponseEntity with the updated AccommodationTypeDTO.
   */
  @PutMapping("/updateAccommodationType")
  public ResponseEntity<AccommodationTypeDTO> updateAccommodationType(@RequestBody AccommodationType accommodationType) {
    try {
      AccommodationTypeDTO updatedAccommodationType = new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
      logger.info("Successfully updated accommodation type with ID: {}", updatedAccommodationType.getAccommodationTypeId());
      return ResponseEntity.ok(updatedAccommodationType);
    } catch (Exception e) {
      logger.error("Error while processing updateAccommodationType request", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Deletes an Accommodation Type by ID.
   *
   * @param accommodationTypeId The ID of the Accommodation Type to be deleted.
   * @return ResponseEntity with a success message.
   */
  @DeleteMapping("/deleteAccommodationTypeById")
  public ResponseEntity<String> deleteAccommodationTypeById(@RequestParam(name = "accommodationTypeId") Long accommodationTypeId) {
    try {
      String result = accommodationTypeServiceImpl.deleteAccommodationTypeById(accommodationTypeId);
      logger.info("Successfully deleted accommodation type with ID: {}", accommodationTypeId);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing deleteAccommodationTypeById request", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
