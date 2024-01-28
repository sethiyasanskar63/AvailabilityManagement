package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity class representing user information.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo {

  private static final Logger logger = LoggerFactory.getLogger(UserInfo.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long userId;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "roles")
  private String roles;
}
