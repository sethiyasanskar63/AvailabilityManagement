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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccommodationType that)) return false;

    if (getAccommodationTypeId() != that.getAccommodationTypeId()) return false;
    if (!getAccommodationTypeName().equals(that.getAccommodationTypeName())) return false;
    if (!getResort().equals(that.getResort())) return false;
    return getAvailabilities().equals(that.getAvailabilities());
  }

  @Override
  public int hashCode() {
    int result = (int) (getAccommodationTypeId() ^ (getAccommodationTypeId() >>> 32));
    result = 31 * result + getAccommodationTypeName().hashCode();
    result = 31 * result + getResort().hashCode();
    result = 31 * result + getAvailabilities().hashCode();
    return result;
  }
}
