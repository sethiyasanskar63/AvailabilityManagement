package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accommodation")
public class Accommodation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "accommodation_id")
  private long accommodationId;

  @Column(name = "accommodation_name")
  private String accommodationName;

  @ManyToOne
  @JoinColumn(name = "accommodation_type_id")
  private AccommodationType accommodationType;

  @OneToMany(mappedBy = "accommodation")
  @ToString.Exclude
  private Set<Availability> availabilities;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Accommodation that)) return false;
    return getAccommodationId() == that.getAccommodationId() && Objects.equals(getAccommodationName(), that.getAccommodationName()) && Objects.equals(getAccommodationType(), that.getAccommodationType()) && Objects.equals(getAvailabilities(), that.getAvailabilities());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAccommodationId(), getAccommodationName(), getAccommodationType(), getAvailabilities());
  }
}
