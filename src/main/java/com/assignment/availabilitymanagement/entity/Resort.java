package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

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

  @Column(name = "resort_name")
  private String resortName;

  @OneToMany(mappedBy = "resort")
  @ToString.Exclude
  private Set<AccommodationType> accommodationTypes;
}
