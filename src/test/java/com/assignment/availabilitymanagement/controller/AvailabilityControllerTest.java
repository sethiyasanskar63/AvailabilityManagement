package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(value = "unchecked")
class AvailabilityControllerTest {

  @Mock
  private AvailabilityService availabilityService;

  @InjectMocks
  private AvailabilityController availabilityController;

  private List<AvailabilityDTO> mockAvailabilities;

  @BeforeEach
  void setUp() {
    AvailabilityDTO availabilityDTO1 = AvailabilityDTO.builder()
        .availabilityId(1L)
        .accommodationTypeId(1L)
        .minNight(2)
        .stayFromDate(LocalDate.parse("2024-01-01"))
        .stayToDate(LocalDate.parse("2024-01-31"))
        .arrivalDays(new int[]{1, 4, 5, 6, 7})
        .departureDays(new int[]{2, 3, 4})
        .build();

    AvailabilityDTO availabilityDTO2 = AvailabilityDTO.builder()
        .accommodationTypeId(2L)
        .minNight(4)
        .stayFromDate(LocalDate.parse("2024-02-01"))
        .stayToDate(LocalDate.parse("2024-02-28"))
        .arrivalDays(new int[]{1, 2, 3})
        .departureDays(new int[]{1, 5, 6, 7})
        .build();

    AvailabilityDTO availabilityDTO3 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(3)
        .stayFromDate(LocalDate.parse("2024-03-01"))
        .stayToDate(LocalDate.parse("2024-03-31"))
        .arrivalDays(new int[]{1, 2, 3, 4, 5, 6, 7})
        .departureDays(new int[]{7})
        .build();
    AvailabilityDTO availabilityDTO4 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(2)
        .stayFromDate(LocalDate.parse("2024-04-01"))
        .stayToDate(LocalDate.parse("2024-04-30"))
        .arrivalDays(new int[]{1, 4, 5, 6, 7})
        .departureDays(new int[]{2, 3, 4})
        .build();

    AvailabilityDTO availabilityDTO5 = AvailabilityDTO.builder()
        .accommodationTypeId(2L)
        .minNight(4)
        .stayFromDate(LocalDate.parse("2024-05-01"))
        .stayToDate(LocalDate.parse("2024-05-31"))
        .arrivalDays(new int[]{1, 2, 3})
        .departureDays(new int[]{1, 5, 6, 7})
        .build();

    AvailabilityDTO availabilityDTO6 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(3)
        .stayFromDate(LocalDate.parse("2024-06-01"))
        .stayToDate(LocalDate.parse("2024-06-30"))
        .arrivalDays(new int[]{1, 2, 3, 4, 5, 6, 7})
        .departureDays(new int[]{7})
        .build();
    AvailabilityDTO availabilityDTO7 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(5)
        .stayFromDate(LocalDate.parse("2024-07-01"))
        .stayToDate(LocalDate.parse("2024-07-31"))
        .arrivalDays(new int[]{1, 6, 7})
        .departureDays(new int[]{2, 3, 4})
        .build();

    AvailabilityDTO availabilityDTO8 = AvailabilityDTO.builder()
        .accommodationTypeId(2L)
        .minNight(4)
        .stayFromDate(LocalDate.parse("2024-08-01"))
        .stayToDate(LocalDate.parse("2024-08-31"))
        .arrivalDays(new int[]{1, 2, 3})
        .departureDays(new int[]{1, 5, 6, 7})
        .build();

    AvailabilityDTO availabilityDTO9 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(3)
        .stayFromDate(LocalDate.parse("2024-09-01"))
        .stayToDate(LocalDate.parse("2024-09-30"))
        .arrivalDays(new int[]{1, 5, 6, 7})
        .departureDays(new int[]{1, 5, 7})
        .build();
    AvailabilityDTO availabilityDTO10 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(2)
        .stayFromDate(LocalDate.parse("2024-10-01"))
        .stayToDate(LocalDate.parse("2024-10-31"))
        .arrivalDays(new int[]{1, 4, 5})
        .departureDays(new int[]{2, 3, 4})
        .build();

    AvailabilityDTO availabilityDTO11 = AvailabilityDTO.builder()
        .accommodationTypeId(2L)
        .minNight(4)
        .stayFromDate(LocalDate.parse("2024-11-01"))
        .stayToDate(LocalDate.parse("2024-11-30"))
        .arrivalDays(new int[]{1, 2, 3})
        .departureDays(new int[]{1, 5, 6, 7})
        .build();

    AvailabilityDTO availabilityDTO12 = AvailabilityDTO.builder()
        .accommodationTypeId(1L)
        .minNight(3)
        .stayFromDate(LocalDate.parse("2024-12-01"))
        .stayToDate(LocalDate.parse("2024-12-31"))
        .arrivalDays(new int[]{1, 2, 3, 7})
        .departureDays(new int[]{7})
        .build();
    mockAvailabilities = Arrays.asList(availabilityDTO1, availabilityDTO2, availabilityDTO3, availabilityDTO4, availabilityDTO5, availabilityDTO6, availabilityDTO7, availabilityDTO8, availabilityDTO9, availabilityDTO10, availabilityDTO11, availabilityDTO12);
    Page<AvailabilityDTO> mockPage = new PageImpl<>(mockAvailabilities, PageRequest.of(0, 10), mockAvailabilities.size());
    lenient().when(availabilityService.getAvailability(any(), any(), any(), any(), any(Pageable.class)))
        .thenReturn(mockPage);
  }

  @Test
  void whenNoParameterProvided_getAllAvailabilities() {
    Pageable pageable = PageRequest.of(0, 10);

    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(null, null, null, null, pageable);

    assertNotNull(response, "Response should not be null.");
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK.");

    assertNotNull(response.getBody(), "Response body should not be null.");
    Page<AvailabilityDTO> responseBody = response.getBody();

    assertEquals(mockAvailabilities.size(), responseBody.getTotalElements(), "The number of elements should match the mock availabilities size.");

    assertEquals(pageable.getPageSize(), responseBody.getSize(), "Page size should match the requested size.");
    assertEquals(pageable.getPageNumber(), responseBody.getNumber(), "Page number should match the requested page.");
  }

  @Test
  void whenAvailabilityIdProvided_getSpecificAvailability() {
    Long availabilityId = 1L;
    Page<AvailabilityDTO> mockPage = new PageImpl<>(Collections.singletonList(mockAvailabilities.get(0)));
    when(availabilityService.getAvailability(eq(availabilityId), any(), any(), any(), any(Pageable.class)))
        .thenReturn(mockPage);

    PageRequest pageRequest = PageRequest.of(0, 10);
    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(null, null, availabilityId, null, pageRequest);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    Page<AvailabilityDTO> responseBody = response.getBody();
    assertEquals(1, responseBody.getTotalElements());
    assertEquals(availabilityId, responseBody.getContent().get(0).getAvailabilityId());
  }

  @Test
  void whenAccommodationTypeIdProvided_getAllAvailabilityForAccommodationType() {
    Long accommodationTypeId = 1L;
    Pageable pageable = PageRequest.of(0, 10);
    Page<AvailabilityDTO> mockPage = new PageImpl<>(mockAvailabilities.stream()
        .filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId))
        .collect(Collectors.toList()), pageable, mockAvailabilities.size());
    when(availabilityService.getAvailability(null, accommodationTypeId, null, null, pageable)).thenReturn(mockPage);
    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(null, null, null, accommodationTypeId, pageable);

    assertNotNull(response, "Response should not be null.");
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK.");

    assertNotNull(response.getBody(), "Response body should not be null.");
    Page<AvailabilityDTO> responseBody = response.getBody();
    assertTrue(responseBody.getContent().stream().allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId)),
        "All returned AvailabilityDTOs should have the requested accommodationTypeId.");
  }

  @Test
  void whenArrivalAndDepartureDatesProvided_getAllAvailabilitiesBetweenThoseDates() {
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    Pageable pageable = PageRequest.of(0, 10);

    Page<AvailabilityDTO> mockPage = new PageImpl<>(mockAvailabilities.stream()
        .filter(a -> !(a.getStayFromDate().isBefore(arrivalDate) || a.getStayToDate().isAfter(departureDate)))
        .collect(Collectors.toList()), pageable, mockAvailabilities.size());
    when(availabilityService.getAvailability(null, null, arrivalDate, departureDate, pageable)).thenReturn(mockPage);

    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(arrivalDate, departureDate, null, null, pageable);

    assertNotNull(response, "Response should not be null.");
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK.");

    assertNotNull(response.getBody(), "Response body should not be null.");
    Page<AvailabilityDTO> responseBody = response.getBody();
    assertTrue(responseBody.getContent().stream()
            .noneMatch(a -> a.getStayFromDate().isBefore(arrivalDate) || a.getStayToDate().isAfter(departureDate)),
        "All returned AvailabilityDTOs should have a stayFromDate on or after the arrival date and a stayToDate on or before the departure date.");
  }

  @Test
  void whenAccommodationTypeIdArrivalAndDepartureDatesProvided_getAllAvailabilitiesBetweenThoseDatesForThatAccommodation() {
    Long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    Pageable pageable = PageRequest.of(0, 10);

    Page<AvailabilityDTO> mockPage = new PageImpl<>(mockAvailabilities.stream()
        .filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId) &&
            !a.getStayFromDate().isBefore(arrivalDate) &&
            !a.getStayToDate().isAfter(departureDate))
        .collect(Collectors.toList()), pageable, mockAvailabilities.size());
    when(availabilityService.getAvailability(eq(null), eq(accommodationTypeId), eq(arrivalDate), eq(departureDate), eq(pageable))).thenReturn(mockPage);

    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(arrivalDate, departureDate, null, accommodationTypeId, pageable);

    assertNotNull(response, "Response should not be null.");
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK.");

    assertNotNull(response.getBody(), "Response body should not be null.");
    Page<AvailabilityDTO> responseBody = response.getBody();

    assertTrue(responseBody.getContent().stream()
            .allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId) &&
                !a.getStayFromDate().isBefore(arrivalDate) &&
                !a.getStayToDate().isAfter(departureDate)),
        "All returned AvailabilityDTOs should match the specified accommodation type ID and be within the given date range.");
  }

  @Test
  void whenInvalidAvailabilityIdProvided_thenReturnError() {
    Long invalidAvailabilityId = 99L;
    Pageable pageable = PageRequest.of(0, 10);

    Page<AvailabilityDTO> emptyPage = Page.empty(pageable);
    when(availabilityService.getAvailability(eq(invalidAvailabilityId), eq(null), eq(null), eq(null), eq(pageable))).thenReturn(emptyPage);

    ResponseEntity<Page<AvailabilityDTO>> response = availabilityController.getAvailability(null, null, invalidAvailabilityId, null, pageable);

    assertNotNull(response, "Response should not be null.");
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Status code should be NO_CONTENT.");

    assertNull(response.getBody(), "Response body should be null for NO_CONTENT status.");
  }

  @Test
  void whenOtherParameterProvidedWithAvailabilityId_thenReturnError() {
    Long availabilityId = 1L;
    Long accommodationTypeId = 1L;
    Pageable pageable = PageRequest.of(0, 10);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            availabilityController.getAvailability(null, null, availabilityId, accommodationTypeId, pageable),
        "Expected getAvailability to throw ResponseStatusException but it didn't");

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Expected status code BAD_REQUEST but found " + exception.getStatusCode());
    assertTrue(Objects.requireNonNull(exception.getReason()).contains("When availabilityId is provided, no other search criteria should be specified."), "Exception reason does not match expected message.");
  }

  @Test
  void whenAddingInvalidAvailability_returnError() {
    AvailabilityDTO invalidAvailability = mockAvailabilities.get(0);
    when(availabilityService.saveAvailabilityFromDTO(any(AvailabilityDTO.class)))
        .thenThrow(new IllegalArgumentException("Invalid Availability Data"));

    ResponseEntity<?> response = availabilityController.addAvailability(invalidAvailability);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assert responseBody != null;
    assertTrue(responseBody.contains("Error adding availability."));
  }

  @Test
  void whenUpdatingInvalidAvailability_returnError() {
    AvailabilityDTO invalidUpdatedAvailability = mockAvailabilities.get(1);
    when(availabilityService.saveAvailabilityFromDTO(any(AvailabilityDTO.class)))
        .thenThrow(new IllegalArgumentException("Invalid Availability Data for Update"));

    ResponseEntity<?> response = availabilityController.updateAvailability(invalidUpdatedAvailability.getAvailabilityId(), invalidUpdatedAvailability);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assert responseBody != null;
    assertTrue(responseBody.contains("Error updating availability."));
  }

  @Test
  void whenDeletingInvalidAvailability_returnError() {
    Long invalidAvailabilityId = 99L;
    doThrow(new NoSuchElementException("No Availability found with ID: " + invalidAvailabilityId))
        .when(availabilityService).deleteAvailabilityById(invalidAvailabilityId);

    ResponseEntity<?> response = availabilityController.deleteAvailabilityById(invalidAvailabilityId);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assert responseBody != null;
    assertTrue(responseBody.contains("Availability not found or already deleted."));
  }

  @Test
  void whenDeletingAvailability_returnSuccessMessage() {
    Long availabilityIdToDelete = mockAvailabilities.get(0).getAvailabilityId();
    doNothing().when(availabilityService).deleteAvailabilityById(availabilityIdToDelete);

    ResponseEntity<?> response = availabilityController.deleteAvailabilityById(availabilityIdToDelete);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assertEquals("Availability deleted successfully.", responseBody);
  }
}
