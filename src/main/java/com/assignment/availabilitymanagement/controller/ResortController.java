package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling Resort-related HTTP requests.
 * Author: Sanskar Sethiya
 */
@RestController
@RequestMapping("/resort")
public class ResortController {

  private static final Logger logger = LoggerFactory.getLogger(ResortController.class);

  @Autowired
  ResortServiceImpl resortServiceImpl;

  /**
   * Retrieves a list of resorts based on optional parameters.
   *
   * @param resortId Optional parameter to filter resorts by ID.
   * @return ResponseEntity containing a list of ResortDTOs or a bad request response.
   */
  @GetMapping("/getResorts")
  public ResponseEntity<List<ResortDTO>> getResorts(@RequestParam(name = "resortId", required = false) Long resortId) {
    try {
      List<ResortDTO> resorts = resortServiceImpl.getResorts(resortId)
          .stream().map(ResortDTO::new).collect(Collectors.toList());
      logger.info("Successfully retrieved resorts");
      return ResponseEntity.ok(resorts);
    } catch (Exception e) {
      logger.error("Error while processing getResorts request", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Adds a new resort.
   *
   * @param resort The Resort object to be added.
   * @return ResponseEntity containing the added ResortDTO or a bad request response.
   */
  @PostMapping("/addResort")
  public ResponseEntity<ResortDTO> addResort(@RequestBody Resort resort) {
    try {
      ResortDTO addedResort = new ResortDTO(resortServiceImpl.saveResort(resort));
      logger.info("Successfully added resort with ID: {}", addedResort.getResortId());
      return ResponseEntity.ok(addedResort);
    } catch (Exception e) {
      logger.error("Error while processing addResort request", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Updates an existing resort.
   *
   * @param resort The Resort object with updated information.
   * @return ResponseEntity containing the updated ResortDTO or a bad request response.
   */
  @PutMapping("/updateResort")
  public ResponseEntity<ResortDTO> updateResort(@RequestBody Resort resort) {
    try {
      ResortDTO updatedResort = new ResortDTO(resortServiceImpl.saveResort(resort));
      logger.info("Successfully updated resort with ID: {}", updatedResort.getResortId());
      return ResponseEntity.ok(updatedResort);
    } catch (Exception e) {
      logger.error("Error while processing updateResort request", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Deletes a resort based on its ID.
   *
   * @param resortId The ID of the resort to be deleted.
   * @return ResponseEntity containing a success message or a bad request response.
   */
  @DeleteMapping("/deleteResortById")
  public ResponseEntity<String> deleteResortById(@RequestParam(name = "resortId") Long resortId) {
    try {
      String result = resortServiceImpl.deleteResortByID(resortId);
      logger.info("Successfully deleted resort with ID: {}", resortId);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      logger.error("Error while processing deleteResortById request", e);
      return ResponseEntity.badRequest().build();
    }
  }
}
