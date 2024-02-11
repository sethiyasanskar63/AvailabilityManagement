package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.AccommodationTypeDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AccommodationType and its DTO AccommodationTypeDTO.
 */
@Mapper(componentModel = "spring")
public interface AccommodationTypeMapper {

  @Mapping(target = "resortId", source = "resort.resortId")
  AccommodationTypeDTO entityToDto(AccommodationType accommodationType);

  @Mapping(target = "resort.resortId", source = "resortId")
  @Mapping(target = "availabilities", ignore = true)
  AccommodationType dtoToEntity(AccommodationTypeDTO dto);
}
