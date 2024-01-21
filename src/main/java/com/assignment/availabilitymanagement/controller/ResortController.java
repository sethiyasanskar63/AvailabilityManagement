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

@RestController
@RequestMapping("/resort")
public class ResortController {

  private static final Logger logger = LoggerFactory.getLogger(ResortController.class);

  @Autowired
  ResortServiceImpl resortServiceImpl;

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
