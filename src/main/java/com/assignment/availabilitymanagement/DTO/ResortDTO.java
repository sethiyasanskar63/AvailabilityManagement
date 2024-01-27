package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Resort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
