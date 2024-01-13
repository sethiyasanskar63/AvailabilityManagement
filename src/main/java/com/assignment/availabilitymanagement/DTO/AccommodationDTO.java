package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Accommodation;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {

  private long accommodationId;
  private String accommodationName;
  private long accommodationTypeId;

  public AccommodationDTO(Accommodation accommodation) {
    this.accommodationId = accommodation.getAccommodationId();
    this.accommodationName = accommodation.getAccommodationName();
    this.accommodationTypeId = accommodation.getAccommodationType().getAccommodationTypeId();
  }
}
