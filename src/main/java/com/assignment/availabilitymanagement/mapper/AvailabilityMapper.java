package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.util.DaysOfWeek;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AvailabilityMapper {

  @Autowired
  protected AccommodationTypeRepository accommodationTypeRepository;

  @BeforeMapping
  protected void idToAccommodationType(AvailabilityDTO dto, @MappingTarget Availability entity) {
    if (dto.getAccommodationTypeId() != null) {
      AccommodationType accommodationType = accommodationTypeRepository.findById(dto.getAccommodationTypeId())
          .orElseThrow(() -> new IllegalArgumentException("Invalid Accommodation Type ID: " + dto.getAccommodationTypeId()));
      entity.setAccommodationType(accommodationType);
    }
  }

  @Mappings({
      @Mapping(target = "accommodationTypeId", source = "accommodationType.accommodationTypeId"),
      @Mapping(target = "arrivalDays", source = "arrivalDays", qualifiedByName = "bitmaskToDaysArray"),
      @Mapping(target = "departureDays", source = "departureDays", qualifiedByName = "bitmaskToDaysArray")
  })
  public abstract AvailabilityDTO toDto(Availability entity);

  @Mappings({
      @Mapping(target = "accommodationType", ignore = true), // Handled by @BeforeMapping
      @Mapping(target = "arrivalDays", source = "arrivalDays", qualifiedByName = "daysArrayToBitmask"),
      @Mapping(target = "departureDays", source = "departureDays", qualifiedByName = "daysArrayToBitmask")
  })
  public abstract Availability toEntity(AvailabilityDTO dto);

  @Named("daysArrayToBitmask")
  public Integer daysArrayToBitmask(int[] days) {
    return DaysOfWeek.setDays(days);
  }

  @Named("bitmaskToDaysArray")
  public int[] bitmaskToDaysArray(Integer bitmask) {
    return DaysOfWeek.getSelectedDays(bitmask);
  }
}
