package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Availability;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvailabilitySpecification implements Specification<Availability> {

  private final Long availabilityId;
  private final Long accommodationId;
  private final Long accommodationTypeId;
  private final Integer minNights;
  private final String arrivalDays;
  private final String departureDays;
  private final Date stayFromDate;
  private final Date stayToDate;

  public AvailabilitySpecification(
      Long availabilityId, Long accommodationId, Long accommodationTypeId,
      Integer minNights, String arrivalDays, String departureDays,
      Date stayFromDate, Date stayToDate) {
    this.availabilityId = availabilityId;
    this.accommodationId = accommodationId;
    this.accommodationTypeId = accommodationTypeId;
    this.minNights = minNights;
    this.arrivalDays = arrivalDays;
    this.departureDays = departureDays;
    this.stayFromDate = stayFromDate;
    this.stayToDate = stayToDate;
  }

  @Override
  public Predicate toPredicate(
      Root<Availability> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    if (availabilityId != null) {
      predicates.add(criteriaBuilder.equal(root.get("availabilityId"), availabilityId));
    }

    if (accommodationId != null) {
      predicates.add(criteriaBuilder.equal(root.get("accommodation").get("accommodationId"), accommodationId));
    }

    if (accommodationTypeId != null) {
      predicates.add(criteriaBuilder.equal(root.get("accommodationType").get("accommodationTypeId"), accommodationTypeId));
    }

    if (minNights != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minNight"), minNights));
    }

    if (arrivalDays != null) {
      predicates.add(criteriaBuilder.equal(root.get("arrivalDays"), arrivalDays));
    }

    if (departureDays != null) {
      predicates.add(criteriaBuilder.equal(root.get("departureDays"), departureDays));
    }

    if (stayFromDate != null || stayToDate != null) {
      Predicate fromPredicate = stayFromDate != null ?
          criteriaBuilder.or(
              criteriaBuilder.isNull(root.get("stayToDate")),
              criteriaBuilder.greaterThanOrEqualTo(root.get("stayToDate"), stayFromDate)
          ) :
          null;

      Predicate toPredicate = stayToDate != null ?
          criteriaBuilder.or(
              criteriaBuilder.isNull(root.get("stayFromDate")),
              criteriaBuilder.lessThanOrEqualTo(root.get("stayFromDate"), stayToDate)
          ) :
          null;

      predicates.add(criteriaBuilder.or(fromPredicate, toPredicate));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
