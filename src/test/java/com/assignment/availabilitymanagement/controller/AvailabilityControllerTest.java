package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl;
import com.assignment.availabilitymanagement.util.PossibleDates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvailabilityControllerTest {

  @Mock
  private AvailabilityServiceImpl availabilityService;

  @InjectMocks
  private AvailabilityController availabilityController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getAvailability() {

    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = LocalDate.now().plusDays(7);
    Long availabilityId = 1L;
    Long accommodationId = 2L;
    Long accommodationTypeId = 3L;

    List<Availability> mockAvailabilities = Arrays.asList(
        new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", null, null),
        new Availability(2L, LocalDate.now(), LocalDate.now().plusDays(14), 3, "Tue", "Sat", null, null)
    );

    when(availabilityService.getAvailability(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate)).thenReturn(mockAvailabilities);

    ResponseEntity<List<AvailabilityDTO>> result = availabilityController.getAvailability(arrivalDate, departureDate, availabilityId, accommodationId, accommodationTypeId);

    assertEquals(ResponseEntity.ok(mockAvailabilities.stream().map(AvailabilityDTO::new).toList()), result);
    verify(availabilityService, times(1)).getAvailability(availabilityId, accommodationId, accommodationTypeId, arrivalDate, departureDate);
  }

  @Test
  void addAvailability() {

    Availability mockAvailability = new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", null, null);

    when(availabilityService.saveAvailability(mockAvailability)).thenReturn(mockAvailability);

    ResponseEntity<AvailabilityDTO> result = availabilityController.addAvailability(mockAvailability);

    assertEquals(ResponseEntity.ok(new AvailabilityDTO(mockAvailability)), result);
    verify(availabilityService, times(1)).saveAvailability(mockAvailability);
  }

  @Test
  void updateAvailability() {

    Availability mockAvailability = new Availability(1L, LocalDate.now(), LocalDate.now().plusDays(7), 2, "Mon", "Sun", null, null);

    when(availabilityService.saveAvailability(mockAvailability)).thenReturn(mockAvailability);

    ResponseEntity<AvailabilityDTO> result = availabilityController.updateAvailability(mockAvailability);

    assertEquals(ResponseEntity.ok(new AvailabilityDTO(mockAvailability)), result);
    verify(availabilityService, times(1)).saveAvailability(mockAvailability);
  }

  @Test
  void deleteAvailabilityById() {

    Long availabilityId = 1L;
    String expectedResult = "Deleted availability with ID: " + availabilityId;

    when(availabilityService.deleteAvailabilityById(availabilityId)).thenReturn(expectedResult);

    ResponseEntity<String> result = availabilityController.deleteAvailabilityById(availabilityId);

    assertEquals(ResponseEntity.ok(expectedResult), result);
    verify(availabilityService, times(1)).deleteAvailabilityById(availabilityId);
  }
}
