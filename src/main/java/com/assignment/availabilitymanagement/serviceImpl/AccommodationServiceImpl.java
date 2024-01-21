package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.repository.AccommodationRepository;
import com.assignment.availabilitymanagement.service.AccommodationService;
import com.assignment.availabilitymanagement.specification.AccommodationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {

  @Autowired
  private AccommodationRepository accommodationRepository;

  @Override
  public List<Accommodation> getAccommodations(Long accommodationId, LocalDate arrivalDate, LocalDate departureDate) {

    AccommodationSpecification accommodationSpecification = new AccommodationSpecification(accommodationId, arrivalDate, departureDate);
    return accommodationRepository.findAll(accommodationSpecification);
  }

  @Override
  public Accommodation saveAccommodation(Accommodation accommodation) {
    return accommodationRepository.saveAndFlush(accommodation);
  }

  @Override
  public String deleteAccommodationById(Long id) {
    accommodationRepository.deleteById(id);
    return "Deleted accommodation ID " + id;
  }
}
