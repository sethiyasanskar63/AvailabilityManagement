package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Accommodation;
import com.assignment.availabilitymanagement.repository.AccommodationRepository;
import com.assignment.availabilitymanagement.specification.AccommodationSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class AccommodationServiceImplTest {

  @Mock
  private AccommodationRepository accommodationRepository;

  @InjectMocks
  private AccommodationServiceImpl accommodationService;

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
    List<Accommodation> expectedAccommodations = Collections.singletonList(accommodation);
    AccommodationSpecification accommodationSpecification = new AccommodationSpecification(accommodationId, arrivalDate, departureDate);
    Mockito.when(accommodationRepository.findAll(accommodationSpecification)).thenReturn(expectedAccommodations);
    List<Accommodation> result = accommodationService.getAccommodations(accommodationId, arrivalDate, departureDate);

    assertEquals(expectedAccommodations, result);
  }

  @Test
  void saveAccommodation_shouldReturnSavedAccommodation() {

    Accommodation accommodationToSave = new Accommodation(1, "Accommodation A", null, null);
    Accommodation savedAccommodation = new Accommodation(1L, "Accommodation A", null, null);
    Mockito.when(accommodationRepository.saveAndFlush(any(Accommodation.class))).thenReturn(savedAccommodation);
    Accommodation result = accommodationService.saveAccommodation(accommodationToSave);

    assertEquals(savedAccommodation, result);
  }

  @Test
  void deleteAccommodationById_shouldReturnDeletedMessage() {

    Long accommodationId = 1L;
    String deletedMessage = "Deleted accommodation ID 1";
    Mockito.doNothing().when(accommodationRepository).deleteById(accommodationId);
    String result = accommodationService.deleteAccommodationById(accommodationId);

    assertEquals(deletedMessage, result);
  }

  @Test
  void deleteAccommodationById_shouldThrowExceptionWhenRepositoryFails() {

    Long accommodationId = 1L;
    Mockito.doThrow(new DataAccessException("Delete failed") {
    }).when(accommodationRepository).deleteById(accommodationId);

    assertThrows(DataAccessException.class, () -> accommodationService.deleteAccommodationById(accommodationId));
  }
}
