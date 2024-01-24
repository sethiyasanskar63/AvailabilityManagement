package com.assignment.availabilitymanagement.entity;

import com.assignment.availabilitymanagement.DTO.AvailabilityDTO;
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
  private Integer arrivalDays;

  @Column(name = "departure_days")
  private Integer departureDays;

  @ManyToOne
  @JoinColumn(name = "accommodation_type_id")
  private AccommodationType accommodationType;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Availability that)) return false;

    if (getMinNight() != that.getMinNight()) return false;
    if (!getAvailabilityId().equals(that.getAvailabilityId())) return false;
    if (!getStayFromDate().equals(that.getStayFromDate())) return false;
    if (!getStayToDate().equals(that.getStayToDate())) return false;
    if (!getArrivalDays().equals(that.getArrivalDays())) return false;
    if (!getDepartureDays().equals(that.getDepartureDays())) return false;
    return getAccommodationType().equals(that.getAccommodationType());
  }

  @Override
  public int hashCode() {
    int result = getAvailabilityId().hashCode();
    result = 31 * result + getStayFromDate().hashCode();
    result = 31 * result + getStayToDate().hashCode();
    result = 31 * result + getMinNight();
    result = 31 * result + getArrivalDays().hashCode();
    result = 31 * result + getDepartureDays().hashCode();
    result = 31 * result + getAccommodationType().hashCode();
    return result;
  }
}
