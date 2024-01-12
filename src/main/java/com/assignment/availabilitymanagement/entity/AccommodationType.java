package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
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
  private Set<Accommodation> accommodations;

  @OneToMany(mappedBy = "accommodationType")
  @ToString.Exclude
  private Set<Availability> availabilities;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccommodationType that)) return false;
    return getAccommodationTypeId() == that.getAccommodationTypeId() && Objects.equals(getAccommodationTypeName(), that.getAccommodationTypeName()) && Objects.equals(getResort(), that.getResort()) && Objects.equals(getAccommodations(), that.getAccommodations()) && Objects.equals(getAvailabilities(), that.getAvailabilities());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAccommodationTypeId(), getAccommodationTypeName(), getResort(), getAccommodations(), getAvailabilities());
  }
}
