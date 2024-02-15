package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import com.assignment.availabilitymanagement.service.ResortService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class ResortControllerTest {

  @Mock
  private ResortService resortService;

  @InjectMocks
  private ResortController resortController;

  @Test
  void whenNoParameterProvided_shouldReturnAllResorts() {
    ResortDTO resortDTO1 = new ResortDTO(1L, "Resort One");
    ResortDTO resortDTO2 = new ResortDTO(2L, "Resort Two");
    ResortDTO resortDTO3 = new ResortDTO(3L, "Resort Three");

    Page<ResortDTO> mockResorts = new PageImpl<>(Arrays.asList(resortDTO1, resortDTO2, resortDTO3));

    when(resortService.getResorts(null, Pageable.ofSize(1)))
        .thenReturn(mockResorts);

    ResponseEntity<Page<ResortDTO>> response = (ResponseEntity<Page<ResortDTO>>) resortController.getResorts(null, Pageable.ofSize(1));

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Page<ResortDTO> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertEquals(3, responseBody.getTotalElements());
  }

  @Test
  void whenResortIdProvided_shouldReturnResortWithGivenId() {
    ResortDTO resortDTO = new ResortDTO(1L, "Resort One");

    Page<ResortDTO> mockResorts = new PageImpl<>(Collections.singletonList(resortDTO));

    when(resortService.getResorts(1L, null))
        .thenReturn(mockResorts);

    ResponseEntity<Page<ResortDTO>> response = (ResponseEntity<Page<ResortDTO>>) resortController.getResorts(1L, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    Page<ResortDTO> responseBody = response.getBody();
    assertNotNull(responseBody);
    assertEquals(1, responseBody.getTotalElements());
    assertEquals(1L, responseBody.getContent().get(0).getResortId());
  }

  @Test
  void whenAddResort_shouldReturnCreatedResort() {
    ResortDTO newResort = new ResortDTO(null, "New Resort");
    ResortDTO savedResort = new ResortDTO(4L, "New Resort");

    when(resortService.saveResort(any(ResortDTO.class))).thenReturn(savedResort);

    ResponseEntity<?> response = resortController.addResort(newResort);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    ResortDTO responseBody = (ResortDTO) response.getBody();
    assertNotNull(responseBody);
    assertEquals("New Resort", responseBody.getResortName());
    assertNotNull(responseBody.getResortId());
  }

  // Test for error when adding a resort
  @Test
  void whenAddInvalidResort_shouldReturnError() {
    ResortDTO invalidResort = new ResortDTO(null, ""); // Assuming this is invalid due to an empty name

    when(resortService.saveResort(any(ResortDTO.class))).thenThrow(new IllegalArgumentException("Invalid Resort Data"));

    ResponseEntity<?> response = resortController.addResort(invalidResort);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String responseBody = (String) response.getBody();
    assert responseBody != null;
    assertTrue(responseBody.contains("Error saving resort"));
  }

  // Test for updating a resort successfully
  @Test
  void whenUpdateResort_shouldReturnUpdatedResort() {
    ResortDTO updateResort = new ResortDTO(1L, "Updated Resort");

    when(resortService.saveResort(any(ResortDTO.class))).thenReturn(updateResort);

    ResponseEntity<?> response = resortController.updateResort(1L, updateResort);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    ResortDTO responseBody = (ResortDTO) response.getBody();
    assertNotNull(responseBody);
    assertEquals("Updated Resort", responseBody.getResortName());
  }

  // Test for error when updating a resort that does not exist
  @Test
  void whenUpdateNonExistingResort_shouldReturnError() {
    ResortDTO nonExistingResort = new ResortDTO(99L, "Non-existing Resort");

    doThrow(new EmptyResultDataAccessException(1)).when(resortService).saveResort(nonExistingResort);

    ResponseEntity<?> response = resortController.updateResort(99L, nonExistingResort);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void whenDeleteResortByID_shouldReturnSuccessMessage() {
    doNothing().when(resortService).deleteResortByID(1L);

    ResponseEntity<?> response = resortController.deleteResortByID(1L);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(((String) Objects.requireNonNull(response.getBody())).contains("successfully deleted"));
  }

  @Test
  void whenDeleteNonExistingResortByID_shouldReturnError() {
    doThrow(new EmptyResultDataAccessException(1)).when(resortService).deleteResortByID(99L);

    ResponseEntity<?> response = resortController.deleteResortByID(99L);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
