package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Override
  public List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                            Integer minNights, String arrivalDays, String departureDays,
                                            Date stayFromDate, Date stayToDate) {


    AvailabilitySpecification availabilitySpecification = new AvailabilitySpecification(availabilityId,accommodationId,accommodationTypeId,minNights,arrivalDays,departureDays,stayFromDate,stayToDate);
    return availabilityRepository.findAll(availabilitySpecification);
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
