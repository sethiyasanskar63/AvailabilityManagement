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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityControllerTest {

  @Mock
  private AvailabilityService availabilityService;

  @InjectMocks
  private AvailabilityController availabilityController;

  private List<AvailabilityDTO> mockAvailabilities;

  @BeforeEach
  void setUp() {
    AvailabilityDTO availability1 = new AvailabilityDTO();
    AvailabilityDTO availability2 = new AvailabilityDTO();
    mockAvailabilities = Arrays.asList(availability1, availability2);
    when(availabilityService.getAvailability(any(), any(), any(), any())).thenReturn(mockAvailabilities);
  }

  @Test
  void whenNoParameterProvided_getAllAvailabilities() {

    ResponseEntity<?> response = availabilityController.getAvailability(null, null, null, null);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    @SuppressWarnings("unchecked")
    List<AvailabilityDTO> responseBody = (List<AvailabilityDTO>) response.getBody();

    assert responseBody != null;
    assertFalse(responseBody.isEmpty());
    assertEquals(mockAvailabilities.size(), responseBody.size());
  }

  // Additional test methods will go here
}
