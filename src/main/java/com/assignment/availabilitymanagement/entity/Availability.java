package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing availability information for an accommodation type,
 * including stay dates, minimum nights, and arrival/departure day restrictions.
 */
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

  @Column(name = "stay_from_date", nullable = false)
  private LocalDate stayFromDate;

  @Column(name = "stay_to_date", nullable = false)
  private LocalDate stayToDate;

  @Column(name = "min_night", nullable = false)
  private int minNight;

  @Column(name = "arrival_days")
  private Integer arrivalDays;

  @Column(name = "departure_days")
  private Integer departureDays;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_type_id", nullable = false)
  private AccommodationType accommodationType;
}
