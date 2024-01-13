package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

  @Autowired
  private AvailabilityServiceImpl availabilityServiceImpl;

  @GetMapping("/getAllAvailability")
  public List<AvailabilityDTO> getAllAvailability() {
    return availabilityServiceImpl.getAllAvailability().stream().map(AvailabilityDTO::new).collect(Collectors.toList());
  }

  @GetMapping("/getAvailabilityById/{id}")
  public AvailabilityDTO getAvailabilityById(@PathVariable("id") Long id) {
    return new AvailabilityDTO(availabilityServiceImpl.getAvailabilityById(id));
  }

  @PostMapping("/addAvailability")
  public AvailabilityDTO addAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @PutMapping("/updateAvailability")
  public AvailabilityDTO updateAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @DeleteMapping("/deleteAvailabilityById/{id}")
  public String deleteAvailabilityById(@PathVariable("id") Long id) {
    return availabilityServiceImpl.deleteAvailabilityById(id);
  }

}
