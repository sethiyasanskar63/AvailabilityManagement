package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.AvailabilityDTO;
import com.assignment.availabilitymanagement.entity.AccommodationType;
import com.assignment.availabilitymanagement.entity.Availability;
import com.assignment.availabilitymanagement.repository.AccommodationTypeRepository;
import com.assignment.availabilitymanagement.util.DaysOfWeek;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for converting between {@link Availability} entities and {@link AvailabilityDTO} data transfer objects.
 * Utilizes MapStruct for mapping fields and custom logic for converting between entity and DTO representations, especially for fields that require conversion between different formats or data structures.
 */
@Mapper(componentModel = "spring")
public abstract class AvailabilityMapper {

  @Autowired
  protected AccommodationTypeRepository accommodationTypeRepository;

  /**
   * Converts the accommodation type ID from the {@link AvailabilityDTO} to an {@link AccommodationType} entity and sets it on the {@link Availability} entity before mapping.
   * This method is called automatically by MapStruct before the main mapping logic.
   *
   * @param dto    The source {@link AvailabilityDTO}.
   * @param entity The target {@link Availability} entity.
   * @throws IllegalArgumentException if the accommodation type ID in the DTO does not correspond to any existing {@link AccommodationType}.
   */
  @BeforeMapping
  protected void idToAccommodationType(AvailabilityDTO dto, @MappingTarget Availability entity) {
    if (dto.getAccommodationTypeId() != null) {
      AccommodationType accommodationType = accommodationTypeRepository.findById(dto.getAccommodationTypeId())
          .orElseThrow(() -> new IllegalArgumentException("Invalid Accommodation Type ID: " + dto.getAccommodationTypeId()));
      entity.setAccommodationType(accommodationType);
    }
  }

  /**
   * Maps an {@link Availability} entity to an {@link AvailabilityDTO}.
   * Custom mappings are defined for converting between integer bitmasks and arrays of days for arrival and departure days.
   *
   * @param entity The source {@link Availability} entity.
   * @return The mapped {@link AvailabilityDTO}.
   */
  @Mappings({
      @Mapping(target = "accommodationTypeId", source = "accommodationType.accommodationTypeId"),
      @Mapping(target = "arrivalDays", source = "arrivalDays", qualifiedByName = "bitmaskToDaysArray"),
      @Mapping(target = "departureDays", source = "departureDays", qualifiedByName = "bitmaskToDaysArray")
  })
  public abstract AvailabilityDTO toDto(Availability entity);

  /**
   * Maps an {@link AvailabilityDTO} to an {@link Availability} entity.
   * The accommodation type is handled separately with a {@link BeforeMapping} method.
   * Custom mappings are defined for converting between arrays of days and integer bitmasks for arrival and departure days.
   *
   * @param dto The source {@link AvailabilityDTO}.
   * @return The mapped {@link Availability} entity.
   */
  @Mappings({
      @Mapping(target = "accommodationType", ignore = true), // Handled by @BeforeMapping
      @Mapping(target = "arrivalDays", source = "arrivalDays", qualifiedByName = "daysArrayToBitmask"),
      @Mapping(target = "departureDays", source = "departureDays", qualifiedByName = "daysArrayToBitmask")
  })
  public abstract Availability toEntity(AvailabilityDTO dto);

  /**
   * Converts an array of day numbers to a bitmask representing the days of the week.
   * This method is intended for use by MapStruct during the mapping process.
   *
   * @param days Array of integers representing days of the week (1 = Monday, 7 = Sunday).
   * @return An integer bitmask representing the specified days.
   */
  @Named("daysArrayToBitmask")
  public Integer daysArrayToBitmask(int[] days) {
    return DaysOfWeek.setDays(days);
  }

  /**
   * Converts a bitmask representing days of the week to an array of day numbers.
   * This method is intended for use by MapStruct during the mapping process.
   *
   * @param bitmask An integer bitmask representing days of the week.
   * @return An array of integers representing the days encoded in the bitmask (1 = Monday, 7 = Sunday).
   */
  @Named("bitmaskToDaysArray")
  public int[] bitmaskToDaysArray(Integer bitmask) {
    return DaysOfWeek.getSelectedDays(bitmask);
  }
}
