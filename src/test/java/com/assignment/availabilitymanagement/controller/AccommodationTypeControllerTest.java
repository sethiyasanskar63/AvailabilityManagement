package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
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

class AccommodationTypeControllerTest {

  @Mock
  private AccommodationTypeService accommodationTypeService;

  @InjectMocks
  private AccommodationTypeController accommodationTypeController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getAccommodationTypes_shouldReturnAccommodationTypes() {

    Long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = LocalDate.now().plusDays(2);
    AccommodationType accommodationType = new AccommodationType(accommodationTypeId, "Type A", null, null, null);
    List<AccommodationTypeDTO> expectedAccommodationTypes = Collections.singletonList(new AccommodationTypeDTO(accommodationType));
    when(accommodationTypeService.getAccommodationTypes(eq(accommodationTypeId), eq(arrivalDate), eq(departureDate)))
        .thenReturn(Collections.singletonList(accommodationType));

    ResponseEntity<List<AccommodationTypeDTO>> result = accommodationTypeController.getAccommodationTypes(accommodationTypeId, arrivalDate, departureDate);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(expectedAccommodationTypes, result.getBody());
  }

  @Test
  void addAccommodationType_shouldReturnAddedAccommodationType() {

    AccommodationType accommodationType = new AccommodationType(1, "Type A", null, null, null);
    when(accommodationTypeService.saveAccommodationType(any(AccommodationType.class))).thenReturn(accommodationType);
    AccommodationTypeDTO expectedAddedAccommodationType = new AccommodationTypeDTO(accommodationType);
    ResponseEntity<AccommodationTypeDTO> result = accommodationTypeController.addAccommodationType(accommodationType);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(expectedAddedAccommodationType, result.getBody());
  }

  @Test
  void updateAccommodationType_shouldReturnUpdatedAccommodationType() {

    AccommodationType accommodationType = new AccommodationType(1L, "Type A", null, null, null);
    when(accommodationTypeService.saveAccommodationType(any(AccommodationType.class))).thenReturn(accommodationType);
    AccommodationTypeDTO expectedUpdatedAccommodationType = new AccommodationTypeDTO(accommodationType);
    ResponseEntity<AccommodationTypeDTO> result = accommodationTypeController.updateAccommodationType(accommodationType);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(expectedUpdatedAccommodationType, result.getBody());
  }

  @Test
  void deleteAccommodationTypeById_shouldReturnDeletedMessage() {

    Long accommodationTypeId = 1L;
    when(accommodationTypeService.deleteAccommodationTypeById(eq(accommodationTypeId))).thenReturn("Deleted accommodation type ID 1");
    ResponseEntity<String> result = accommodationTypeController.deleteAccommodationTypeById(accommodationTypeId);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Deleted accommodation type ID 1", result.getBody());
  }
}
