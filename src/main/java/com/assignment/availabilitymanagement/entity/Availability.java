package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Builder
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
  private long availabilityId;

  @Column(name = "stay_from_date")
  private Date stayFromDate;

  @Column(name = "stay_to_date")
  private Date stayToDate;

  @Column(name = "min_night")
  private int minNight;

  @Column(name = "max_night")
  private int maxNight;

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
    return getAvailabilityId() == that.getAvailabilityId() && getMinNight() == that.getMinNight() && getMaxNight() == that.getMaxNight() && Objects.equals(getStayFromDate(), that.getStayFromDate()) && Objects.equals(getStayToDate(), that.getStayToDate()) && Objects.equals(getArrivalDays(), that.getArrivalDays()) && Objects.equals(getDepartureDays(), that.getDepartureDays()) && Objects.equals(getAccommodationType(), that.getAccommodationType()) && Objects.equals(getAccommodation(), that.getAccommodation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAvailabilityId(), getStayFromDate(), getStayToDate(), getMinNight(), getMaxNight(), getArrivalDays(), getDepartureDays(), getAccommodationType(), getAccommodation());
  }
}
