package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accommodationType")
public class AccommodationTypeController {

  @Autowired
  AccommodationTypeServiceImpl accommodationTypeServiceImpl;

  @GetMapping("/getAllAccommodationTypes")
  public List<AccommodationTypeDTO> getAllAccommodationTypes() {
    return accommodationTypeServiceImpl.getAllAccommodationTypes().stream().map(AccommodationTypeDTO::new).collect(Collectors.toList());
  }

  @GetMapping("/getAccommodationTypeById/{id}")
  public AccommodationTypeDTO getAccommodationTypeById(@PathVariable("id") Long id) {
    return new AccommodationTypeDTO(accommodationTypeServiceImpl.getAccommodationTypeById(id));
  }

  @PostMapping("/addAccommodationType")
  public AccommodationTypeDTO addAccommodationType(@RequestBody AccommodationType accommodationType) {
    return new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
  }

  @PutMapping("/updateAccommodationType")
  public AccommodationTypeDTO updateAccommodationType(@RequestBody AccommodationType accommodationType) {
    return new AccommodationTypeDTO(accommodationTypeServiceImpl.saveAccommodationType(accommodationType));
  }

  @DeleteMapping("/deleteAccommodationTypeById/{id}")
  public String deleteAccommodationTypeById(@PathVariable("id") Long id) {
    return accommodationTypeServiceImpl.deleteAccommodationTypeById(id);
  }
}
