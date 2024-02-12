package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.mapper.AccommodationTypeMapper;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class AccommodationTypeServiceImplTest {

  @Mock
  private AccommodationTypeRepository accommodationTypeRepository;

  @Mock
  private AccommodationTypeMapper accommodationTypeMapper;

  @InjectMocks
  private AccommodationTypeServiceImpl accommodationTypeService;

  private AccommodationTypeDTO accommodationTypeDTO;
  private AccommodationType accommodationType;

  @BeforeEach
  void setUp() {
    accommodationTypeDTO = new AccommodationTypeDTO(1L, "Type 1", 1);
    accommodationType = new AccommodationType();
    accommodationType.setAccommodationTypeId(1L);
    accommodationType.setAccommodationTypeName("Type 1");

    lenient().when(accommodationTypeMapper.dtoToEntity(any(AccommodationTypeDTO.class))).thenReturn(accommodationType);
    lenient().when(accommodationTypeMapper.entityToDto(any(AccommodationType.class))).thenReturn(accommodationTypeDTO);
    lenient().when(accommodationTypeRepository.save(any(AccommodationType.class))).thenReturn(accommodationType);
  }

  @Test
  void getAccommodationTypes() {
    when(accommodationTypeRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(accommodationType));

    List<AccommodationTypeDTO> result = accommodationTypeService.getAccommodationTypes(null, LocalDate.now(), LocalDate.now().plusDays(1));

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(accommodationTypeDTO.getAccommodationTypeId(), result.get(0).getAccommodationTypeId());
  }

  @Test
  void saveAccommodationType_New() {
    AccommodationTypeDTO result = accommodationTypeService.saveAccommodationType(accommodationTypeDTO);

    assertNotNull(result);
    assertEquals(accommodationTypeDTO.getAccommodationTypeId(), result.getAccommodationTypeId());
  }

  @Test
  void deleteAccommodationTypeById_Success() {
    doNothing().when(accommodationTypeRepository).deleteById(eq(1L));

    assertDoesNotThrow(() -> accommodationTypeService.deleteAccommodationTypeById(1L));
    verify(accommodationTypeRepository).deleteById(1L);
  }

  @Test
  void deleteAccommodationTypeById_NotFound() {
    doThrow(new EmptyResultDataAccessException(1)).when(accommodationTypeRepository).deleteById(eq(99L));

    assertThrows(RuntimeException.class, () -> accommodationTypeService.deleteAccommodationTypeById(99L));
  }
}
