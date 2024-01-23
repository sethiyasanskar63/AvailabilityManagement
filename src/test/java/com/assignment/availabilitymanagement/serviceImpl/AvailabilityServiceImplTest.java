package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import com.assignment.availabilitymanagement.util.WorkBookToAvailability;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AvailabilityServiceImplTest {

  @Mock
  private AvailabilityRepository availabilityRepository;

  @Mock
  private WorkBookToAvailability workBookToAvailability;

  @InjectMocks
  private AvailabilityServiceImpl availabilityService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getAvailability() {
    // Arrange
    Long availabilityId = 1L;
    Long accommodationId = 2L;
    Long accommodationTypeId = 3L;
    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = LocalDate.now().plusDays(7);

    AvailabilitySpecification availabilitySpecification = new AvailabilitySpecification(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate);

    List<Availability> mockAvailabilities = Arrays.asList(
        new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", new AccommodationType(), null),
        new Availability(2L, LocalDate.now(), LocalDate.now().plusDays(14), 3, "Tue", "Sat", new AccommodationType(), null)
    );

    when(availabilityRepository.findAll(availabilitySpecification)).thenReturn(mockAvailabilities);

    List<Availability> result = availabilityService.getAvailability(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate);

    assertEquals(mockAvailabilities, result);
    verify(availabilityRepository, times(1)).findAll(availabilitySpecification);
  }

  @Test
  void saveAvailability() {

    Availability mockAvailability = new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", new AccommodationType(), null);
    when(availabilityRepository.saveAndFlush(mockAvailability)).thenReturn(mockAvailability);

    Availability result = availabilityService.saveAvailability(mockAvailability);

    assertEquals(mockAvailability, result);
    verify(availabilityRepository, times(1)).saveAndFlush(mockAvailability);
  }

  @Test
  void saveAllAvailability() {

    Workbook mockWorkbook = mock(Workbook.class);
    List<Availability> mockAvailabilities = Arrays.asList(
        new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", new AccommodationType(), null),
        new Availability(2L, LocalDate.now(), LocalDate.now().plusDays(14), 3, "Tue", "Sat", new AccommodationType(), null)
    );

    when(workBookToAvailability.excelToAvailability(mockWorkbook)).thenReturn(mockAvailabilities);
    String result = availabilityService.saveAllAvailability(mockWorkbook);

    assertEquals("Availabilities Imported", result);
    verify(availabilityRepository, times(1)).saveAllAndFlush(mockAvailabilities);
  }

  @Test
  void deleteAvailabilityById() {

    Long availabilityId = 1L;

    String result = availabilityService.deleteAvailabilityById(availabilityId);

    assertEquals("Deleted availability with ID: 1", result);
    verify(availabilityRepository, times(1)).deleteById(availabilityId);
  }
}
