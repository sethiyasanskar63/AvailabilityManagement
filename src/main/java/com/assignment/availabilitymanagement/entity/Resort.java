package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Entity class representing a resort.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resort")
public class Resort {

  private static final Logger logger = LoggerFactory.getLogger(Resort.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resort_id")
  private long resortId;

  @Column(name = "resort_name")
  private String resortName;

  @OneToMany(mappedBy = "resort")
  @ToString.Exclude
  private Set<AccommodationType> accommodationTypes;
}
