package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.ResortDTO;
import com.assignment.availabilitymanagement.entity.Resort;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Resort and its DTO ResortDTO.
 */
@Mapper(componentModel = "spring")
public interface ResortMapper {

  ResortDTO toDto(Resort resort);

  Resort toEntity(ResortDTO dto);
}
