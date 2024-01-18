package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationDTO;
import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {

  @Autowired
  AccommodationServiceImpl accommodationServiceImpl;

  @GetMapping("/getAccommodations")
  public List<AccommodationDTO> getAccommodations(@RequestParam(name = "accommodationId", required = false) Long accommodationId) {
    if (accommodationId == null) {
      return accommodationServiceImpl.getAllAccommodations()
          .stream()
          .map(AccommodationDTO::new)
          .collect(Collectors.toList());
    } else {
      return accommodationServiceImpl.getAccommodationById(accommodationId) != null ? List.of() : Collections.emptyList();
    }
  }

  @PostMapping("/addAccommodation")
  public AccommodationDTO addAccommodation(@RequestBody Accommodation accommodation) {
    return new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
  }

  @PutMapping("/updateAccommodation")
  public AccommodationDTO updateAccommodation(@RequestBody Accommodation accommodation) {
    return new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
  }

  @DeleteMapping("/deleteAccommodationById")
  public String deleteAccommodationById(@RequestParam(name = "accommodationId") Long accommodationId) {
    return accommodationServiceImpl.deleteAccommodationById(accommodationId);
  }
}
