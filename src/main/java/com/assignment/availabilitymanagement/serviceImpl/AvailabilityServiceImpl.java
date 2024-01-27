package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.entity.DaysOfWeek;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import com.assignment.availabilitymanagement.util.WorkBookToAvailability;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

  @Autowired
  private AccommodationTypeServiceImpl accommodationTypeServiceImpl;
  @Autowired
  private AvailabilityRepository availabilityRepository;
  @Autowired
  private WorkBookToAvailability workBookToAvailability;

  @Override
  public List<Availability> getAvailability(Long availabilityId, Long accommodationTypeId,
                                            LocalDate arrivalDate, LocalDate departureDate) {

    try {
      AvailabilitySpecification availabilitySpecification = new AvailabilitySpecification(availabilityId, accommodationTypeId, arrivalDate, departureDate);
      return availabilityRepository.findAll(availabilitySpecification);
    } catch (Exception e) {
      logger.error("Error while getting availability", e);
      throw new RuntimeException("Error while getting availability", e);
    }
  }

  @Override
  public void saveAvailabilityFromDTO(AvailabilityDTO availabilityDTO) {
    Availability availability = new Availability(
        availabilityDTO.getAvailabilityId(),
        availabilityDTO.getStayFromDate(),
        availabilityDTO.getStayToDate(),
        availabilityDTO.getMinNight(),
        DaysOfWeek.setDays(availabilityDTO.getArrivalDays()),
        DaysOfWeek.setDays(availabilityDTO.getDepartureDays()),
        accommodationTypeServiceImpl.getAccommodationTypes(availabilityDTO.getAccommodationTypeId(), null, null).get(0)
    );

    try {
      availabilityRepository.saveAndFlush(availability);
    } catch (Exception e) {
      logger.error("Error while saving availability", e);
      throw new RuntimeException("Error while saving availability", e);
    }
  }

  @Override
  public String saveAllAvailability(Workbook workbook) {
    try {
      List<Availability> availabilities = workBookToAvailability.excelToAvailability(workbook);
      availabilityRepository.saveAllAndFlush(availabilities);
      return "Availabilities Imported";
    } catch (Exception e) {
      logger.error("Error while saving all availabilities", e);
      throw new RuntimeException("Error while saving all availabilities", e);
    }
  }

  @Override
  public String deleteAvailabilityById(Long id) {
    try {
      availabilityRepository.deleteById(id);
      return "Deleted availability with ID: " + id;
    } catch (Exception e) {
      logger.error("Error while deleting availability", e);
      throw new RuntimeException("Error while deleting availability", e);
    }
  }
}
