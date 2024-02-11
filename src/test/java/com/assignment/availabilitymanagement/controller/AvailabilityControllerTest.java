package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.service.AvailabilityService;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
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
import java.util.NoSuchElementException;
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
        .departureDays(new int[]{1,5,7})
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
    mockAvailabilities = Arrays.asList(availabilityDTO1, availabilityDTO2,availabilityDTO3,availabilityDTO4,availabilityDTO5,availabilityDTO6,availabilityDTO7,availabilityDTO8,availabilityDTO9,availabilityDTO10,availabilityDTO11,availabilityDTO12);
    when(availabilityService.getAvailability(any(), any(), any(), any())).thenReturn(mockAvailabilities);
    lenient().when(availabilityService.getAvailability(any(), any(), any(), any()))
        .thenReturn(mockAvailabilities);
  }

  @Test
  void whenNoParameterProvided_getAllAvailabilities() {
    ResponseEntity<?> response = availabilityController.getAvailability(null, null, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertEquals(mockAvailabilities.size(), responseBody.size());
  }

  @Test
  void whenAvailabilityIdProvided_getSpecificAvailability() {
    Long availabilityId = 1L;
    when(availabilityService.getAvailability(availabilityId, null, null, null)).thenReturn(Arrays.asList(mockAvailabilities.get(0)));

    ResponseEntity<?> response = availabilityController.getAvailability(null, null, availabilityId, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertEquals(1, responseBody.size());
    assertEquals(availabilityId, responseBody.get(0).getAvailabilityId());
  }

  @Test
  void whenAccommodationTypeIdProvided_getAllAvailabilityForAccommodationType() {
    Long accommodationTypeId = 1L;
    when(availabilityService.getAvailability(null, accommodationTypeId, null, null)).thenReturn( mockAvailabilities.stream().filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId)).collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(null, null, null, accommodationTypeId);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream().allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId)));
  }

  @Test
  void whenArrivalDateProvided_getAllAvailabilitiesAfterThatDate() {
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    when(availabilityService.getAvailability(null, null, arrivalDate, null)).thenReturn(mockAvailabilities.stream().filter(a -> !a.getStayFromDate().isBefore(arrivalDate)).collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(arrivalDate, null, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream().noneMatch(a -> a.getStayFromDate().isBefore(arrivalDate)));
  }

  @Test
  void whenDepartureDateProvided_getAllAvailabilitiesTillThatDate() {
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    when(availabilityService.getAvailability(null, null, null, departureDate)).thenReturn(mockAvailabilities.stream().filter(a -> !a.getStayToDate().isAfter(departureDate)).collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(null, departureDate, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream().noneMatch(a -> a.getStayToDate().isAfter(departureDate)));
  }

  @Test
  void whenArrivalAndDepartureDatesProvided_getAllAvailabilitiesBetweenThoseDates() {
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    when(availabilityService.getAvailability(null, null, arrivalDate, departureDate))
        .thenReturn(mockAvailabilities.stream()
            .filter(a -> !(a.getStayFromDate().isBefore(arrivalDate) || a.getStayToDate().isAfter(departureDate)))
            .collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(arrivalDate, departureDate, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream()
        .allMatch(a -> !(a.getStayFromDate().isBefore(arrivalDate) || a.getStayToDate().isAfter(departureDate))));
  }

  @Test
  void whenAccommodationTypeIdAndArrivalDateProvided_getAllAvailabilitiesFromThatDateForThatAccommodation() {
    Long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    when(availabilityService.getAvailability(null, accommodationTypeId, arrivalDate, null))
        .thenReturn(mockAvailabilities.stream()
            .filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId) && !a.getStayFromDate().isBefore(arrivalDate))
            .collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(arrivalDate, null, null, accommodationTypeId);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream()
        .allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId) && !a.getStayFromDate().isBefore(arrivalDate)));
  }

  @Test
  void whenAccommodationTypeIdAndDepartureDateProvided_getAllAvailabilitiesTillThatDateForThatAccommodation() {
    Long accommodationTypeId = 2L;
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    when(availabilityService.getAvailability(null, accommodationTypeId, null, departureDate))
        .thenReturn(mockAvailabilities.stream()
            .filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId) && !a.getStayToDate().isAfter(departureDate))
            .collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(null, departureDate, null, accommodationTypeId);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream()
        .allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId) && !a.getStayToDate().isAfter(departureDate)));
  }

  @Test
  void whenAccommodationTypeIdArrivalAndDepartureDatesProvided_getAllAvailabilitiesBetweenThoseDatesForThatAccommodation() {
    Long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.parse("2024-06-01");
    LocalDate departureDate = LocalDate.parse("2024-06-30");
    when(availabilityService.getAvailability(null, accommodationTypeId, arrivalDate, departureDate))
        .thenReturn(mockAvailabilities.stream()
            .filter(a -> a.getAccommodationTypeId().equals(accommodationTypeId) &&
                !a.getStayFromDate().isBefore(arrivalDate) &&
                !a.getStayToDate().isAfter(departureDate))
            .collect(Collectors.toList()));

    ResponseEntity<?> response = availabilityController.getAvailability(arrivalDate, departureDate, null, accommodationTypeId);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();
    assertTrue(responseBody.stream()
        .allMatch(a -> a.getAccommodationTypeId().equals(accommodationTypeId) &&
            !a.getStayFromDate().isBefore(arrivalDate) &&
            !a.getStayToDate().isAfter(departureDate)));
  }

  @Test
  void whenInvalidAvailabilityIdProvided_thenReturnError() {
    Long invalidAvailabilityId = 99L;
    when(availabilityService.getAvailability(invalidAvailabilityId, null, null, null)).thenReturn(List.of());

    ResponseEntity<?> response = availabilityController.getAvailability(null, null, invalidAvailabilityId, null);

    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  void whenOtherParameterProvidedWithAvailabilityId_thenReturnError() {
    Long availabilityId = 1L;
    Long accommodationTypeId = 1L;
    ResponseEntity<?> response = availabilityController.getAvailability(null, null, availabilityId, accommodationTypeId);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assertEquals("When availabilityId is provided, no other search criteria should be specified.", responseBody);
  }

  @Test
  void whenAddingAvailability_returnSavedAvailability() {
    AvailabilityDTO newAvailability = mockAvailabilities.get(0);
    when(availabilityService.saveAvailabilityFromDTO(any(AvailabilityDTO.class))).thenReturn(newAvailability);

    ResponseEntity<?> response = availabilityController.addAvailability(newAvailability);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    AvailabilityDTO responseBody = (AvailabilityDTO) response.getBody();
    assertNotNull(responseBody);
    assertEquals(newAvailability, responseBody);
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
  void whenUpdatingAvailability_returnUpdatedAvailability() {
    AvailabilityDTO updatedAvailability = mockAvailabilities.get(1);
    when(availabilityService.updateAvailabilityFromDTO(any(AvailabilityDTO.class))).thenReturn(updatedAvailability);

    ResponseEntity<?> response = availabilityController.updateAvailability(updatedAvailability.getAvailabilityId(), updatedAvailability);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    AvailabilityDTO responseBody = (AvailabilityDTO) response.getBody();
    assertNotNull(responseBody);
    assertEquals(updatedAvailability, responseBody);
  }

  @Test
  void whenUpdatingInvalidAvailability_returnError() {
    AvailabilityDTO invalidUpdatedAvailability = mockAvailabilities.get(1);
    when(availabilityService.updateAvailabilityFromDTO(any(AvailabilityDTO.class)))
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
