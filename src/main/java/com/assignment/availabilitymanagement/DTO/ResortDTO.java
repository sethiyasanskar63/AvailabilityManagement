package com.assignment.availabilitymanagement.DTO;

import com.assignment.availabilitymanagement.entity.Resort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) for Resort entity.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResortDTO {

  private Long resortId;
  private String resortName;

  /**
   * Constructs a ResortDTO object using the information from a Resort entity.
   *
   * @param resort The Resort entity.
   */
  public ResortDTO(Resort resort) {
    this.resortId = resort.getResortId();
    this.resortName = resort.getResortName();
  }
}
