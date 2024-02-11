package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

/**
 * Represents an accommodation type within a resort, establishing a many-to-one relationship with Resort
 * and a one-to-many relationship with Availability.
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

  @Column(name = "accommodation_type_name", nullable = false)
  private String accommodationTypeName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resort_id", nullable = false)
  private Resort resort;

  @OneToMany(mappedBy = "accommodationType", cascade = CascadeType.ALL)
  private Set<Availability> availabilities;
}
