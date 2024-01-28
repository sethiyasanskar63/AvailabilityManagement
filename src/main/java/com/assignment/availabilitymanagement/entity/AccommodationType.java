package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Entity class representing AccommodationType.
 * Author: Sanskar Sethiya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accommodation_type")
public class AccommodationType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "accommodation_type_id")
  private long accommodationTypeId;

  @Column(name = "accommodation_type_name")
  private String accommodationTypeName;

  @ManyToOne
  @JoinColumn(name = "resort_id", nullable = false)
  private Resort resort;

  @OneToMany(mappedBy = "accommodationType")
  @ToString.Exclude
  private Set<Availability> availabilities;

}
