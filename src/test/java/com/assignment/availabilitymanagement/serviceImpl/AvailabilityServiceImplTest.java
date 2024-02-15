package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class AvailabilityServiceImplTest {

  @Mock
  private AvailabilityRepository availabilityRepository;

  @Mock
  private AccommodationTypeRepository accommodationTypeRepository;

  @Mock
  private AvailabilityMapper availabilityMapper;

  @InjectMocks
  private AvailabilityServiceImpl availabilityService;

  private AvailabilityDTO availabilityDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    AccommodationType accommodationType = new AccommodationType();
    accommodationType.setAccommodationTypeId(1L);
    accommodationType.setAccommodationTypeName("Test Accommodation");
    accommodationType.setResort(new Resort(1L, "Test Resort"));

    Availability availability = new Availability();
    availability.setAvailabilityId(1L);
    availability.setAccommodationType(accommodationType);

    availabilityDTO = new AvailabilityDTO();
    availabilityDTO.setAvailabilityId(1L);
    availabilityDTO.setAccommodationTypeId(1L);

    // Setup mocks
    lenient().when(availabilityMapper.toDto(any(Availability.class))).thenReturn(availabilityDTO);
    lenient().when(availabilityMapper.toEntity(any(AvailabilityDTO.class))).thenReturn(availability);
    lenient().when(accommodationTypeRepository.findById(anyLong())).thenReturn(Optional.of(accommodationType));
    lenient().when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);
    lenient().when(availabilityRepository.findAll(any(Specification.class), any(Sort.class)))
        .thenReturn(Collections.singletonList(availability));
  }

  @Test
  void getAvailability() {
    // Execute the service method with specific criteria
    List<AvailabilityDTO> result = availabilityService.getAvailability(null, 1L, null, null);

    // Assertions to validate the expected outcome
    assertNotNull(result, "The result should not be null.");
    assertFalse(result.isEmpty(), "The result list should not be empty.");
    assertEquals(1, result.size(), "The result list should contain exactly one element.");
    AvailabilityDTO returnedDTO = result.get(0);
    assertEquals(availabilityDTO.getAvailabilityId(), returnedDTO.getAvailabilityId(), "The availability ID should match the expected value.");
    assertEquals(availabilityDTO.getAccommodationTypeId(), returnedDTO.getAccommodationTypeId(), "The accommodation type ID should match the expected value.");
  }


  @Test
  void saveAvailabilityFromDTO() {
    String result = availabilityService.saveAvailabilityFromDTO(availabilityDTO);

    assertNotNull(result);
    assertEquals("Availability Saved", result);
  }

  @Test
  void deleteAvailabilityById() {
    doNothing().when(availabilityRepository).deleteById(anyLong());

    assertDoesNotThrow(() -> availabilityService.deleteAvailabilityById(1L));
    verify(availabilityRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteAvailabilityById_ThrowsException() {
    doThrow(new DataIntegrityViolationException("Cannot delete")).when(availabilityRepository).deleteById(anyLong());

    assertThrows(IllegalStateException.class, () -> availabilityService.deleteAvailabilityById(1L));
  }
}
