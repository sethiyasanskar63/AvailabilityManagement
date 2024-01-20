package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationDTO;
import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {

  @Autowired
  AccommodationServiceImpl accommodationServiceImpl;

  @GetMapping("/getAccommodations")
  public ResponseEntity<List<AccommodationDTO>> getAccommodations(
      @RequestParam(name = "accommodationId", required = false) Long accommodationId,

      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate arrivalDate,

      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate departureDate
  ) {

    if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(accommodationServiceImpl.getAccommodations(accommodationId, arrivalDate, departureDate)
        .stream().map(AccommodationDTO::new).collect(Collectors.toList()));
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
