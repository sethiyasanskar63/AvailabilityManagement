package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import com.assignment.availabilitymanagement.mapper.ResortMapper;
import com.assignment.availabilitymanagement.repository.ResortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResortServiceImplTest {

  @Mock
  private ResortRepository resortRepository;

  @Mock
  private ResortMapper resortMapper;

  @InjectMocks
  private ResortServiceImpl resortService;

  private ResortDTO resortDTO;

  @BeforeEach
  void setUp() {
    resortDTO = new ResortDTO(1L, "Resort 1");
    Resort resort = new Resort();
    resort.setResortId(1L);
    resort.setResortName("Resort 1");

    lenient().when(resortMapper.toEntity(any(ResortDTO.class))).thenReturn(resort);
    lenient().when(resortMapper.toDto(any(Resort.class))).thenReturn(resortDTO);
    lenient().when(resortRepository.save(any(Resort.class))).thenReturn(resort);
  }

  @Test
  void saveResort() {
    ResortDTO result = resortService.saveResort(resortDTO);

    assertNotNull(result);
    assertEquals(resortDTO.getResortId(), result.getResortId());
  }

  @Test
  void deleteResortByID_Success() {
    doNothing().when(resortRepository).deleteById(eq(1L));

    assertDoesNotThrow(() -> resortService.deleteResortByID(1L));
    verify(resortRepository).deleteById(1L);
  }

  @Test
  void deleteResortByID_NotFound() {
    doThrow(new EmptyResultDataAccessException(1)).when(resortRepository).deleteById(eq(99L));

    assertThrows(NoSuchElementException.class, () -> resortService.deleteResortByID(99L));
  }
}
