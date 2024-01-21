package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
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
  private String arrivalDays;

  @Column(name = "departure_days")
  private String departureDays;

  @ManyToOne
  @JoinColumn(name = "accommodation_type_id")
  private AccommodationType accommodationType;

  @ManyToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Availability that)) return false;
    return getAvailabilityId() == that.getAvailabilityId() && getMinNight() == that.getMinNight() && Objects.equals(getStayFromDate(), that.getStayFromDate()) && Objects.equals(getStayToDate(), that.getStayToDate()) && Objects.equals(getArrivalDays(), that.getArrivalDays()) && Objects.equals(getDepartureDays(), that.getDepartureDays()) && Objects.equals(getAccommodationType(), that.getAccommodationType()) && Objects.equals(getAccommodation(), that.getAccommodation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAvailabilityId(), getStayFromDate(), getStayToDate(), getMinNight(), getArrivalDays(), getDepartureDays(), getAccommodationType(), getAccommodation());
  }
}
