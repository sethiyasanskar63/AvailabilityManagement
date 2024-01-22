package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class ResortControllerTest {

  @Mock
  private ResortServiceImpl resortService;

  @InjectMocks
  private ResortController resortController;

  @Test
  void getResorts_shouldReturnListOfResorts_whenNoParameterPassed() {

    ResortDTO resortDTO = new ResortDTO(new Resort(1L, "Resort A", null));
    Mockito.when(resortService.getResorts(null)).thenReturn(Collections.singletonList(new Resort(1L, "Resort A", null)));
    ResponseEntity<List<ResortDTO>> responseEntity = resortController.getResorts(null);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(1, responseEntity.getBody().size());
    assertEquals(resortDTO, responseEntity.getBody().get(0));
  }

  @Test
  void getResorts_shouldReturnResortWithGivenId_whenParameterPassed() {

    Long resortId = 1L;
    ResortDTO resortDTO = new ResortDTO(new Resort(resortId, "Resort A", null));
    Mockito.when(resortService.getResorts(resortId)).thenReturn(Collections.singletonList(new Resort(resortId, "Resort A", null)));
    ResponseEntity<List<ResortDTO>> responseEntity = resortController.getResorts(resortId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(1, responseEntity.getBody().size());
    assertEquals(resortDTO, responseEntity.getBody().get(0));
  }

  @Test
  void getResorts_shouldReturnEmptyList_whenNoResortsFound() {

    Long resortId = 1L;
    Mockito.when(resortService.getResorts(resortId)).thenReturn(Collections.emptyList());
    ResponseEntity<List<ResortDTO>> responseEntity = resortController.getResorts(resortId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(0, responseEntity.getBody().size());
  }

  @Test
  void addResort_shouldReturnAddedResort() {

    ResortDTO addedResortDTO = new ResortDTO(new Resort(1L, "Resort A", null));
    Resort resortToAdd = new Resort(1, "Resort A", null);
    Mockito.when(resortService.saveResort(any(Resort.class))).thenReturn(new Resort(1L, "Resort A", null));
    ResponseEntity<ResortDTO> responseEntity = resortController.addResort(resortToAdd);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(addedResortDTO, responseEntity.getBody());
  }

  @Test
  void updateResort_shouldReturnUpdatedResort() {

    long resortId = 1L;
    ResortDTO updatedResortDTO = new ResortDTO(new Resort(resortId, "Updated Resort A", null));
    Resort resortToUpdate = new Resort(resortId, "Updated Resort A", null);
    Mockito.when(resortService.saveResort(any(Resort.class))).thenReturn(new Resort(resortId, "Updated Resort A", null));
    ResponseEntity<ResortDTO> responseEntity = resortController.updateResort(resortToUpdate);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(updatedResortDTO, responseEntity.getBody());
  }

  @Test
  void deleteResortById_shouldReturnDeletedMessage() {

    Long resortId = 1L;
    String deletedMessage = "Deleted resort ID: 1";
    Mockito.when(resortService.deleteResortByID(resortId)).thenReturn(deletedMessage);
    ResponseEntity<String> responseEntity = resortController.deleteResortById(resortId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
    assertEquals(deletedMessage, responseEntity.getBody());
  }
}