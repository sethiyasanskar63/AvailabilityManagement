package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.service.AccommodationTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(value = "unchecked")
class AccommodationTypeControllerTest {

  @Mock
  private AccommodationTypeService accommodationTypeService;

  @InjectMocks
  private AccommodationTypeController accommodationTypeController;

  @BeforeEach
  void setUp() {
    List<AccommodationTypeDTO> mockAccommodations = Arrays.asList(
        new AccommodationTypeDTO(1L, "Type 1", 1),
        new AccommodationTypeDTO(2L, "Type 2", 2),
        new AccommodationTypeDTO(3L, "Type 3", 3)
    );

    lenient().when(accommodationTypeService.getAccommodationTypes(null, null, null, Pageable.unpaged()))
        .thenReturn(new PageImpl<>(mockAccommodations));
    lenient().when(accommodationTypeService.getAccommodationTypes(eq(1L), isNull(), isNull(), any(Pageable.class)))
        .thenReturn(new PageImpl<>(Collections.singletonList(mockAccommodations.get(0))));
    lenient().when(accommodationTypeService.getAccommodationTypes(isNull(), any(LocalDate.class), isNull(), any(Pageable.class)))
        .thenReturn(new PageImpl<>(mockAccommodations.subList(0, 2)));
    lenient().when(accommodationTypeService.getAccommodationTypes(isNull(), isNull(), any(LocalDate.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(mockAccommodations.subList(1, 3)));
    lenient().when(accommodationTypeService.getAccommodationTypes(isNull(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(mockAccommodations));
  }


  @Test
  void whenNoParameterProvided_shouldReturnAllAccommodationTypes() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, null, null, Pageable.unpaged());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertInstanceOf(Page.class, response.getBody());
    assertEquals(3, ((Page<AccommodationTypeDTO>) response.getBody()).getTotalElements());
  }

  @Test
  void whenAccommodationTypeIdProvided_shouldReturnAccommodationTypeWithGivenId() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(1L, null, null, Pageable.unpaged());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertInstanceOf(Page.class, response.getBody());
    assertEquals(1, ((Page<AccommodationTypeDTO>) response.getBody()).getTotalElements());
    assertEquals(1L, ((Page<AccommodationTypeDTO>) response.getBody()).getContent().get(0).getAccommodationTypeId());
  }

  @Test
  void whenArrivalAndDepartureDatesProvided_shouldReturnAccommodationTypesWithAvailabilityBetweenGivenDates() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(null, LocalDate.of(2024, 4, 1), LocalDate.of(2024, 10, 1), Pageable.unpaged());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertInstanceOf(Page.class, response.getBody());
    assertFalse(((Page<AccommodationTypeDTO>) response.getBody()).getContent().isEmpty());
  }

  @Test
  void whenOtherParameterProvidedWithAccommodationTypeId_shouldReturnError() {
    ResponseEntity<?> response = accommodationTypeController.getAccommodationTypes(1L, LocalDate.of(2024, 4, 1), null, Pageable.ofSize(1));

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Cannot specify accommodationTypeId together with arrivalDate or departureDate."));
  }

  @Test
  void whenAddingAccommodationType_shouldReturnSavedAccommodationType() {
    AccommodationTypeDTO newAccommodationType = new AccommodationTypeDTO(4L, "New Type", 4);
    AccommodationTypeDTO savedAccommodationType = new AccommodationTypeDTO(4L, "New Type", 4);

    when(accommodationTypeService.saveAccommodationType(any(AccommodationTypeDTO.class))).thenReturn(savedAccommodationType);

    ResponseEntity<?> response = accommodationTypeController.addAccommodationType(newAccommodationType);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(savedAccommodationType, response.getBody());
  }

  @Test
  void whenAddingAccommodationTypeWithError_shouldReturnBadRequest() {
    AccommodationTypeDTO invalidAccommodationType = new AccommodationTypeDTO(20L, "", 0); // Assuming this is invalid

    when(accommodationTypeService.saveAccommodationType(any(AccommodationTypeDTO.class)))
        .thenThrow(new IllegalArgumentException("Invalid accommodation type details"));

    ResponseEntity<?> response = accommodationTypeController.addAccommodationType(invalidAccommodationType);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void whenDeletingAccommodationType_shouldReturnSuccessMessage() {
    doNothing().when(accommodationTypeService).deleteAccommodationTypeById(1L);

    ResponseEntity<?> response = accommodationTypeController.deleteAccommodationTypeById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Accommodation type with ID: 1 was successfully deleted.", response.getBody());
  }

  @Test
  void whenDeletingNonexistentAccommodationType_shouldReturnNotFound() {
    doThrow(new IllegalArgumentException("Accommodation type not found")).when(accommodationTypeService).deleteAccommodationTypeById(99L);

    ResponseEntity<?> response = accommodationTypeController.deleteAccommodationTypeById(99L);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
