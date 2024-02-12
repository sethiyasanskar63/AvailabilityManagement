package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a resort, which can have multiple accommodation types.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resort")
public class Resort {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resort_id")
  private long resortId;

  @Column(name = "resort_name", nullable = false)
  private String resortName;
}
