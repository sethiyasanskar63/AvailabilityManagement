package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.repository.AccommodationRepository;
import com.assignment.availabilitymanagement.service.AccommodationService;
import com.assignment.availabilitymanagement.specification.AccommodationSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationServiceImpl.class);

  @Autowired
  private AccommodationRepository accommodationRepository;

  @Override
  public List<Accommodation> getAccommodations(Long accommodationId, LocalDate arrivalDate, LocalDate departureDate) {
    try {
      AccommodationSpecification accommodationSpecification = new AccommodationSpecification(accommodationId, arrivalDate, departureDate);
      return accommodationRepository.findAll(accommodationSpecification);
    } catch (DataAccessException e) {
      logger.error("Error while fetching accommodations from the database", e);
      throw e;
    }
  }

  @Override
  public Accommodation saveAccommodation(Accommodation accommodation) {
    try {
      return accommodationRepository.saveAndFlush(accommodation);
    } catch (DataAccessException e) {
      logger.error("Error while saving accommodation to the database", e);
      throw e;
    }
  }

  @Override
  public String deleteAccommodationById(Long id) {
    try {
      accommodationRepository.deleteById(id);
      return "Deleted accommodation ID " + id;
    } catch (DataAccessException e) {
      logger.error("Error while deleting accommodation from the database", e);
      throw e;
    }
  }
}
