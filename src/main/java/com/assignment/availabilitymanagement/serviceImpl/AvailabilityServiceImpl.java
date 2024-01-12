package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Override
  public List<Availability> getAllAvailability() {
    return availabilityRepository.findAll();
  }

  @Override
  public Availability getAvailabilityById(Long id) {
    return availabilityRepository.findById(id).orElse(null);
  }

  @Override
  public Availability saveAvailability(Availability availability) {
    return availabilityRepository.saveAndFlush(availability);
  }

  @Override
  public String deleteAvailabilityById(Long id) {
    availabilityRepository.deleteById(id);
    return "Deleted availability with ID: " + id;
  }
}
