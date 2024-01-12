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
@Table(name = "resort")
public class Resort {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resort_id")
  private long resortId;

  @Column(name = "resort_name")
  private String resortName;

  @OneToMany(mappedBy = "resort")
  @ToString.Exclude
  private Set<AccommodationType> accommodationTypes;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Resort resort)) return false;
    return getResortId() == resort.getResortId() && Objects.equals(getResortName(), resort.getResortName()) && Objects.equals(getAccommodationTypes(), resort.getAccommodationTypes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getResortId(), getResortName(), getAccommodationTypes());
  }
}
