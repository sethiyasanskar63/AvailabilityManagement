package com.assignment.availabilitymanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) for User authentication.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private String username;
  private String password;

}
