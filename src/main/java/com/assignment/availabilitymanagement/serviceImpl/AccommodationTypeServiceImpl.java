package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import com.assignment.availabilitymanagement.specification.AccommodationTypeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeServiceImpl.class);

  @Autowired
  private AccommodationTypeRepository accommodationTypeRepository;

  @Override
  public List<AccommodationType> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    try {
      AccommodationTypeSpecification accommodationTypeSpecification = new AccommodationTypeSpecification(accommodationTypeId, arrivalDate, departureDate);
      return accommodationTypeRepository.findAll(accommodationTypeSpecification);
    } catch (DataAccessException e) {
      logger.error("Error while fetching accommodation types from the database", e);
      throw e;
    }
  }

  @Override
  public AccommodationType saveAccommodationType(AccommodationType accommodationType) {
    try {
      return accommodationTypeRepository.saveAndFlush(accommodationType);
    } catch (DataAccessException e) {
      logger.error("Error while saving accommodation type to the database", e);
      throw e;
    }
  }

  @Override
  public String deleteAccommodationTypeById(Long id) {
    try {
      accommodationTypeRepository.deleteById(id);
      return "Deleted accommodation type ID " + id;
    } catch (DataAccessException e) {
      logger.error("Error while deleting accommodation type from the database", e);
      throw e;
    }
  }
}
