package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.repository.ResortRepository;
import com.assignment.availabilitymanagement.specification.ResortSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class ResortServiceImplTest {

  @Mock
  private ResortRepository resortRepository;

  @InjectMocks
  private ResortServiceImpl resortService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getResorts_shouldReturnResorts() {

    Long resortId = 1L;
    Resort resort = new Resort(resortId, "Resort A", null);
    List<Resort> expectedResorts = Collections.singletonList(resort);
    ResortSpecification resortSpecification = new ResortSpecification(resortId);
    Mockito.when(resortRepository.findAll(resortSpecification)).thenReturn(expectedResorts);
    List<Resort> result = resortService.getResorts(resortId);

    assertEquals(expectedResorts, result);
  }

  @Test
  void saveResort_shouldReturnSavedResort() {

    Resort resortToSave = new Resort(1, "Resort A", null);
    Resort savedResort = new Resort(1L, "Resort A", null);
    Mockito.when(resortRepository.saveAndFlush(any(Resort.class))).thenReturn(savedResort);
    Resort result = resortService.saveResort(resortToSave);

    assertEquals(savedResort, result);
  }

  @Test
  void deleteResortByID_shouldReturnDeletedMessage() {

    Long resortId = 1L;
    String deletedMessage = "Deleted resort ID: 1";
    Mockito.doNothing().when(resortRepository).deleteById(resortId);
    String result = resortService.deleteResortByID(resortId);

    assertEquals(deletedMessage, result);
  }

  @Test
  void deleteResortByID_shouldThrowExceptionWhenRepositoryFails() {

    Long resortId = 1L;
    Mockito.doThrow(new RuntimeException("Delete failed")).when(resortRepository).deleteById(resortId);

    assertThrows(RuntimeException.class, () -> resortService.deleteResortByID(resortId));
  }
}
