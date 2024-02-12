package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.mapper.AvailabilityMapper;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.repository.AvailabilityRepository;
import com.assignment.availabilitymanagement.specification.AvailabilitySpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
  private Availability availability;

  @BeforeEach
  void setUp() {
    AccommodationType accommodationType = new AccommodationType();
    accommodationType.setAccommodationTypeId(1L);
    accommodationType.setAccommodationTypeName("Test Accommodation");
    accommodationType.setResort(new Resort(1L, "Test Resort"));

    availability = new Availability();
    availability.setAvailabilityId(1L);
    availability.setAccommodationType(accommodationType);

    availabilityDTO = new AvailabilityDTO();
    availabilityDTO.setAvailabilityId(1L);
    availabilityDTO.setAccommodationTypeId(1L);

    lenient().when(availabilityMapper.toDto(any(Availability.class))).thenReturn(availabilityDTO);
    lenient().when(availabilityMapper.toEntity(any(AvailabilityDTO.class))).thenReturn(availability);
    lenient().when(accommodationTypeRepository.findById(anyLong())).thenReturn(Optional.of(accommodationType));
    lenient().when(availabilityRepository.save(any(Availability.class))).thenReturn(availability);
  }

  @Test
  void getAvailability() {
    when(availabilityRepository.findAll(any(AvailabilitySpecification.class))).thenReturn(Collections.singletonList(availability));

    List<AvailabilityDTO> result = availabilityService.getAvailability(null, 1L, LocalDate.now(), LocalDate.now().plusDays(1));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(availabilityDTO.getAvailabilityId(), result.get(0).getAvailabilityId());
  }

  @Test
  void saveAvailabilityFromDTO() {
    AvailabilityDTO result = availabilityService.saveAvailabilityFromDTO(availabilityDTO);

    assertNotNull(result);
    assertEquals(availabilityDTO.getAvailabilityId(), result.getAvailabilityId());
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
