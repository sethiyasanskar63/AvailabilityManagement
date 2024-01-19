package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodationType")
public class AccommodationTypeController {

  @Autowired
  AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  @GetMapping("/getAccommodationTypes")
  public ResponseEntity<List<AccommodationTypeDTO>> getAccommodationTypes(
      @RequestParam(name = "accommodationTypeId", required = false) Long accommodationTypeId,

      @RequestParam(name = "arrivalDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate arrivalDate,

      @RequestParam(name = "departureDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate departureDate
  ) {

    if ((arrivalDate == null && departureDate != null) || (arrivalDate != null && departureDate == null)) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(accommodationTypeServiceImpl.getAccommodationTypes(accommodationTypeId, arrivalDate, departureDate)
        .stream().map(AccommodationTypeDTO::new).collect(Collectors.toList()));
  }

  @PostMapping("/addAccommodationType")
  public AccommodationTypeDTO addAccommodationType(@RequestBody AccommodationType accommodationType) {
    return new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
  }

  @PutMapping("/updateAccommodationType")
  public AccommodationTypeDTO updateAccommodationType(@RequestBody AccommodationType accommodationType) {
    return new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
  }

  @DeleteMapping("/deleteAccommodationTypeById")
  public String deleteAccommodationTypeById(@RequestParam(name = "accommodationTypeId") Long accommodationTypeId) {
    return accommodationTypeServiceImpl.deleteAccommodationTypeById(accommodationTypeId);
  }
}
