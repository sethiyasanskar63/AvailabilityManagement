package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import com.assignment.availabilitymanagement.specification.AccommodationTypeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService {

  @Autowired
  private AccommodationTypeRepository accommodationTypeRepository;

  @Override
  public List<AccommodationType> getAccommodationTypes(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {

    AccommodationTypeSpecification accommodationTypeSpecification = new AccommodationTypeSpecification(accommodationTypeId,arrivalDate,departureDate);
    return accommodationTypeRepository.findAll(accommodationTypeSpecification);
  }

  @Override
  public AccommodationType saveAccommodationType(AccommodationType accommodationType) {
    return accommodationTypeRepository.saveAndFlush(accommodationType);
  }

  @Override
  public String deleteAccommodationTypeById(Long id) {
    accommodationTypeRepository.deleteById(id);
    return "Deleted accommodation type ID " + id;
  }
}
