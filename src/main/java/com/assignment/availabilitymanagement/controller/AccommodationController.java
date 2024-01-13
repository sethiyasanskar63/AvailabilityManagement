package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationDTO;
import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {

  @Autowired
  AccommodationServiceImpl accommodationServiceImpl;

  @GetMapping("/getAllAccommodations")
  public List<AccommodationDTO> getAllAccommodations() {
    return accommodationServiceImpl.getAllAccommodations().stream().map(AccommodationDTO::new).collect(Collectors.toList());
  }

  @GetMapping("/getAccommodationById/{id}")
  public AccommodationDTO getAccommodationById(@PathVariable("id") Long id) {
    return new AccommodationDTO(accommodationServiceImpl.getAccommodationById(id));
  }

  @PostMapping("/addAccommodation")
  public AccommodationDTO addAccommodation(@RequestBody Accommodation accommodation) {
    return new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
  }

  @PutMapping("/updateAccommodation")
  public AccommodationDTO updateAccommodation(@RequestBody Accommodation accommodation) {
    return new AccommodationDTO(accommodationServiceImpl.saveAccommodation(accommodation));
  }

  @DeleteMapping("/deleteAccommodationById/{id}")
  public String deleteAccommodationById(@PathVariable("id") Long id) {
    return accommodationServiceImpl.deleteAccommodationById(id);
  }
}
