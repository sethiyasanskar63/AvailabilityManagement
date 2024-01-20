package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import com.assignment.availabilitymanagement.util.ExcelToAvailability;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Override
  public List<Availability> getAvailability(Long availabilityId, Long accommodationId, Long accommodationTypeId,
                                            LocalDate arrivalDate, LocalDate departureDate) {

    AvailabilitySpecification availabilitySpecification = new AvailabilitySpecification(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate);
    return availabilityRepository.findAll(availabilitySpecification);
  }

  @Override
  public Availability saveAvailability(Availability availability) {
    return availabilityRepository.saveAndFlush(availability);
  }

  @Override
  public String saveAllAvailabilityFromWorkbook(Workbook workbook) {
    List<Availability> availabilities = new ExcelToAvailability().getExcelToAvailability(workbook);
    availabilityRepository.saveAllAndFlush(availabilities);
    return "Availabilities Imported";
  }

  @Override
  public String deleteAvailabilityById(Long id) {
    availabilityRepository.deleteById(id);
    return "Deleted availability with ID: " + id;
  }
}
