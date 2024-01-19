package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

  @Autowired
  private AvailabilityServiceImpl availabilityServiceImpl;

  @GetMapping("/getAvailability")
  public List<AvailabilityDTO> getAvailability(
      @RequestParam(name = "stayFromDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") Date stayFromDate,

      @RequestParam(name = "stayToDate", required = false)
      @DateTimeFormat(pattern = "yyyy-mm-dd") Date stayToDate,

      @RequestParam(name = "availabilityId", required = false) Long availabilityId,

      @RequestParam(name = "accommodationId", required = false) Long accommodationId,

      @RequestParam(name = "accommodationTypeId", required = false) Long accommodationTypeId,

      @RequestParam(name = "minNights", required = false) Integer minNights,

      @RequestParam(name = "arrivalDays", required = false) String arrivalDays,

      @RequestParam(name = "departureDays", required = false) String departureDays
  ) {
    return availabilityServiceImpl.getAvailability(availabilityId, accommodationId, accommodationTypeId, minNights, arrivalDays, departureDays, stayFromDate, stayToDate)
        .stream().map(AvailabilityDTO::new).collect(Collectors.toList());
  }

  @PostMapping("/addAvailability")
  public AvailabilityDTO addAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @PutMapping("/updateAvailability")
  public AvailabilityDTO updateAvailability(@RequestBody Availability availability) {
    return new AvailabilityDTO(availabilityServiceImpl.saveAvailability(availability));
  }

  @DeleteMapping("/deleteAvailabilityById")
  public String deleteAvailabilityById(@RequestParam(name = "availabilityId") Long availabilityId) {
    return availabilityServiceImpl.deleteAvailabilityById(availabilityId);
  }
}
