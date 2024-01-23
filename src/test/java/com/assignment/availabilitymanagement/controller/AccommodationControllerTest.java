package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationDTO;
import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.serviceImpl.AccommodationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AccommodationControllerTest {

  @Mock
  private AccommodationServiceImpl accommodationService;

  @InjectMocks
  private AccommodationController accommodationController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getAccommodations_shouldReturnAccommodations() {

    Long accommodationId = 1L;
    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = LocalDate.now().plusDays(2);
    Accommodation accommodation = new Accommodation(accommodationId, "Accommodation A", null, null);
    List<AccommodationDTO> expectedAccommodations = Collections.singletonList(new AccommodationDTO(accommodation));
    when(accommodationService.getAccommodations(eq(accommodationId), eq(arrivalDate), eq(departureDate)))
        .thenReturn(Collections.singletonList(accommodation));

    ResponseEntity<List<AccommodationDTO>> result = accommodationController.getAccommodations(accommodationId, arrivalDate, departureDate);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(expectedAccommodations, result.getBody());
  }

  @Test
  void getAccommodations_shouldReturnBadRequestWhenInvalidDateParameters() {

    Long accommodationId = 1L;
    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = null;
    ResponseEntity<List<AccommodationDTO>> result = accommodationController.getAccommodations(accommodationId, arrivalDate, departureDate);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  void addAccommodation_shouldReturnAddedAccommodation() {

    Accommodation accommodationTest = new Accommodation(1, "Accommodation A", null, null);
    AccommodationDTO accommodationDTO = new AccommodationDTO(accommodationTest);
    Accommodation accommodation = new Accommodation(accommodationDTO.getAccommodationId(), accommodationDTO.getAccommodationName(), null, null);
    when(accommodationService.saveAccommodation(any(Accommodation.class))).thenReturn(accommodation);
    ResponseEntity<AccommodationDTO> result = accommodationController.addAccommodation(accommodation);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(accommodationDTO, result.getBody());
  }

  @Test
  void addAccommodation_shouldReturnInternalServerErrorWhenServiceFails() {

    Accommodation accommodation = new Accommodation(1, "Accommodation A", null, null);
    when(accommodationService.saveAccommodation(any(Accommodation.class))).thenThrow(new RuntimeException("Save failed"));
    ResponseEntity<AccommodationDTO> result = accommodationController.addAccommodation(accommodation);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
  }

  @Test
  void updateAccommodation_shouldReturnUpdatedAccommodation() {

    Accommodation accommodationTest = new Accommodation(1, "Accommodation A", null, null);
    AccommodationDTO accommodationDTO = new AccommodationDTO(accommodationTest);
    Accommodation accommodation = new Accommodation(accommodationDTO.getAccommodationId(), accommodationDTO.getAccommodationName(), null, null);
    when(accommodationService.saveAccommodation(any(Accommodation.class))).thenReturn(accommodation);
    ResponseEntity<AccommodationDTO> result = accommodationController.updateAccommodation(accommodation);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(accommodationDTO, result.getBody());
  }

  @Test
  void updateAccommodation_shouldReturnInternalServerErrorWhenServiceFails() {

    Accommodation accommodation = new Accommodation(1, "Accommodation A", null, null);
    when(accommodationService.saveAccommodation(any(Accommodation.class))).thenThrow(new RuntimeException("Update failed"));
    ResponseEntity<AccommodationDTO> result = accommodationController.updateAccommodation(accommodation);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
  }

  @Test
  void deleteAccommodationById_shouldReturnDeletedMessage() {

    Long accommodationId = 1L;
    when(accommodationService.deleteAccommodationById(eq(accommodationId))).thenReturn("Deleted accommodation ID 1");
    ResponseEntity<String> result = accommodationController.deleteAccommodationById(accommodationId);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Deleted accommodation ID 1", result.getBody());
  }

  @Test
  void deleteAccommodationById_shouldReturnInternalServerErrorWhenServiceFails() {

    Long accommodationId = 1L;
    when(accommodationService.deleteAccommodationById(eq(accommodationId))).thenThrow(new RuntimeException("Delete failed"));
    ResponseEntity<String> result = accommodationController.deleteAccommodationById(accommodationId);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
  }
}
