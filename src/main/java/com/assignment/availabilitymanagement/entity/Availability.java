package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availability")
public class Availability {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "availability_id")
  private Long availabilityId;

  @Temporal(TemporalType.DATE)
  @Column(name = "stay_from_date")
  private LocalDate stayFromDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "stay_to_date")
  private LocalDate stayToDate;

  @Column(name = "min_night")
  private int minNight;

  @Column(name = "arrival_days")
  private Integer arrivalDays;

  @Column(name = "departure_days")
  private Integer departureDays;

  @ManyToOne
  @JoinColumn(name = "accommodation_type_id")
  private AccommodationType accommodationType;

}
