package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeDTO {

  private long accommodationTypeId;
  private String accommodationTypeName;
  private long resort_id;

  public AccommodationTypeDTO(AccommodationType accommodationType) {
    this.accommodationTypeId = accommodationType.getAccommodationTypeId();
    this.accommodationTypeName = accommodationType.getAccommodationTypeName();
    this.resort_id = accommodationType.getResort().getResortId();
  }
}
