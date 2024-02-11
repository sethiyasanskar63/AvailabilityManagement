package com.assignment.availabilitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user authentication data.
 * It carries user data between processes, focusing on the needs of authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  @NotBlank(message = "Username cannot be blank.")
  private String username;

  @NotBlank(message = "Password cannot be blank.")
  private String password;
}
