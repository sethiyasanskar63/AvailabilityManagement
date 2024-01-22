package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.specification.AccommodationTypeSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccommodationTypeServiceImplTest {

  @Mock
  private AccommodationTypeRepository accommodationTypeRepository;

  @InjectMocks
  private AccommodationTypeServiceImpl accommodationTypeService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getAccommodationTypes_shouldReturnAccommodationTypes() {

    long accommodationTypeId = 1L;
    LocalDate arrivalDate = LocalDate.now();
    LocalDate departureDate = LocalDate.now().plusDays(2);
    AccommodationType accommodationType = new AccommodationType(accommodationTypeId, "Type A", null, null, null);
    List<AccommodationType> expectedAccommodationTypes = Collections.singletonList(accommodationType);
    when(accommodationTypeRepository.findAll(any(AccommodationTypeSpecification.class)))
        .thenReturn(Collections.singletonList(accommodationType));

    List<AccommodationType> result = accommodationTypeService.getAccommodationTypes(accommodationTypeId, arrivalDate, departureDate);

    assertEquals(expectedAccommodationTypes, result);
  }

  @Test
  void saveAccommodationType_shouldReturnSavedAccommodationType() {

    AccommodationType accommodationType = new AccommodationType(1,"Type A", null, null, null);
    when(accommodationTypeRepository.saveAndFlush(any(AccommodationType.class))).thenReturn(accommodationType);
    AccommodationType result = accommodationTypeService.saveAccommodationType(accommodationType);

    assertEquals(accommodationType, result);
  }

  @Test
  void deleteAccommodationTypeById_shouldReturnDeletedMessage() {
    Long accommodationTypeId = 1L;

    String result = accommodationTypeService.deleteAccommodationTypeById(accommodationTypeId);

    assertEquals("Deleted accommodation type ID 1", result);
    verify(accommodationTypeRepository, times(1)).deleteById(eq(accommodationTypeId));
  }
}
