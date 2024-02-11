package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class AccommodationTypeControllerTest {

  @Mock
  private AccommodationTypeService accommodationTypeService;

  @InjectMocks
  private AccommodationTypeController accommodationTypeController;

  @BeforeEach
  void setUp() {
    List<AccommodationTypeDTO> mockAccommodationTypes = Arrays.asList(
        new AccommodationTypeDTO(1L, "Type 1", 1),
        new AccommodationTypeDTO(2L, "Type 2", 1)
    );

    lenient().when(accommodationTypeService.getAccommodationTypes(null, null, null)).thenReturn(mockAccommodationTypes);
    lenient().when(accommodationTypeService.getAccommodationTypes(1L, null, null)).thenReturn(List.of(mockAccommodationTypes.get(0)));
  }

  @Test
  void whenNoParameterProvided_shouldReturnAllAccommodationTypes() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AccommodationTypeDTO> responseBody = (List<AccommodationTypeDTO>) response.getBody();
    assertNotNull(responseBody);
    assertEquals(2, responseBody.size());
  }

  @Test
  void whenAccommodationTypeIdProvided_shouldReturnAccommodationTypeWithGivenId() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(1L, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AccommodationTypeDTO> responseBody = (List<AccommodationTypeDTO>) response.getBody();
    assertNotNull(responseBody);
    assertEquals(1, responseBody.size());
    assertEquals(1L, responseBody.get(0).getAccommodationTypeId());
  }

  @Test
  void whenOtherParameterProvidedWithAccommodationTypeId_shouldReturnError() {
    Long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.of(2024, 4, 1);

    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(accommodationTypeId, arrivalDate, null);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Cannot specify accommodationTypeId together with arrivalDate or departureDate."));
  }

  @Test
  void whenArrivalDateProvided_shouldReturnAccommodationTypesWithAvailabilityAfterGivenDate() {
    LocalDate arrivalDate = LocalDate.of(2024, 4, 1);
    lenient().when(accommodationTypeService.getAccommodationTypes(null, arrivalDate, null)).thenReturn(List.of());

    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, arrivalDate, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AccommodationTypeDTO> responseBody = (List<AccommodationTypeDTO>) response.getBody();
  }

  @Test
  void whenDepartureDateProvided_shouldReturnAccommodationTypesWithAvailabilityBeforeGivenDate() {
    LocalDate departureDate = LocalDate.of(2024, 10, 1);
    lenient().when(accommodationTypeService.getAccommodationTypes(null, null, departureDate)).thenReturn(List.of());

    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, null, departureDate);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AccommodationTypeDTO> responseBody = (List<AccommodationTypeDTO>) response.getBody();
  }

  @Test
  void whenArrivalAndDepartureDatesProvided_shouldReturnAccommodationTypesWithAvailabilityBetweenGivenDates() {
    LocalDate arrivalDate = LocalDate.of(2024, 4, 1);
    LocalDate departureDate = LocalDate.of(2024, 10, 1);
    lenient().when(accommodationTypeService.getAccommodationTypes(null, arrivalDate, departureDate)).thenReturn(List.of());

    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, arrivalDate, departureDate);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AccommodationTypeDTO> responseBody = (List<AccommodationTypeDTO>) response.getBody();
  }
}
