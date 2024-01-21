package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationDTO;
import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationController.class);

  @Autowired
  AccommodationServiceImpl accommodationServiceImpl;

  @GetMapping("/getAccommodations")
  public ResponseEntity<List<AccommodationDTO>> getAccommodations(
      @RequestParam(name = "accommodationId", required = false) Long accommodationId,
      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate) {

    try {
      if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
        return ResponseEntity.badRequest().build();
      }

      List<AccommodationDTO> result = accommodationServiceImpl.getAccommodations(accommodationId, arrivalDate, departureDate)
          .stream().map(AccommodationDTO::new).collect(Collectors.toList());

      logger.info("Successfully retrieved accommodations");
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing getAccommodations request", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/addAccommodation")
  public ResponseEntity<AccommodationDTO> addAccommodation(@RequestBody Accommodation accommodation) {
    try {
      AccommodationDTO result = new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
      logger.info("Successfully added accommodation with ID: {}", result.getAccommodationId());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing addAccommodation request", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PutMapping("/updateAccommodation")
  public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody Accommodation accommodation) {
    try {
      AccommodationDTO result = new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
      logger.info("Successfully updated accommodation with ID: {}", result.getAccommodationId());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing updateAccommodation request", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping("/deleteAccommodationById")
  public ResponseEntity<String> deleteAccommodationById(@RequestParam(name = "accommodationId") Long accommodationId) {
    try {
      String result = accommodationServiceImpl.deleteAccommodationById(accommodationId);
      logger.info("Successfully deleted accommodation with ID: {}", accommodationId);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing deleteAccommodationById request", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
