package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.DTO.UserDTO;
import com.assignment.availabilitymanagement.entity.UserInfo;
import com.assignment.availabilitymanagement.security.JwtService;
import com.assignment.availabilitymanagement.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/addUser")
  @PreAuthorize("hasAnyAuthority('ADMIN_ROLES')")
  public ResponseEntity<String> addUser(@RequestBody UserInfo userInfo) {
    try {
      String response = userService.addUser(userInfo);
      logger.info("User added successfully: {}", userInfo.getUsername());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      logger.error("Error adding user", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
    try {
      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

      if (authenticate.isAuthenticated()) {
        String token = jwtService.generateToken(userDTO.getUsername());
        logger.info("User logged in successfully: {}", userDTO.getUsername());
        return ResponseEntity.ok(token);
      } else {
        throw new UsernameNotFoundException("Invalid user request");
      }
    } catch (Exception e) {
      logger.error("Error logging in", e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}
