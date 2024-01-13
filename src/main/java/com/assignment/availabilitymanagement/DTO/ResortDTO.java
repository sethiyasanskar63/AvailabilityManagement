package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Resort;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResortDTO {

  private Long resortId;
  private String resortName;

  public ResortDTO(Resort resort) {
    this.resortId = resort.getResortId();
    this.resortName = resort.getResortName();
  }
}
