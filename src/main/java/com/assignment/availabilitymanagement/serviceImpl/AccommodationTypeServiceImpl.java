package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService {

  @Autowired
  private AccommodationTypeRepository accommodationTypeRepository;

  @Override
  public List<AccommodationType> getAllAccommodationTypes() {
    return accommodationTypeRepository.findAll();
  }

  @Override
  public AccommodationType getAccommodationTypeById(Long id) {
    return accommodationTypeRepository.findById(id).orElse(null);
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
