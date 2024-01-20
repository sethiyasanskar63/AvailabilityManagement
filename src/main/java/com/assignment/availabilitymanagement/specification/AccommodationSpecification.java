package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Accommodation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationSpecification implements Specification<Accommodation> {

  private final Long accommodationId;
  private final LocalDate arrivalDate;
  private final LocalDate departureDate;

  public AccommodationSpecification(Long accommodationId, LocalDate arrivalDate, LocalDate departureDate) {
    this.accommodationId = accommodationId;
    this.arrivalDate = arrivalDate;
    this.departureDate = departureDate;
  }

  @Override
  public Predicate toPredicate(Root<Accommodation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    if (accommodationId != null) {
      predicates.add(criteriaBuilder.equal(root.get("accommodationId"), accommodationId));
    }

    if (arrivalDate != null && departureDate != null) {
      predicates.add(criteriaBuilder.or(
          criteriaBuilder.and(
              criteriaBuilder.lessThanOrEqualTo(root.join("availabilities").get("stayFromDate"), arrivalDate),
              criteriaBuilder.greaterThanOrEqualTo(root.join("availabilities").get("stayToDate"), arrivalDate)
          )
      ));

      predicates.add(criteriaBuilder.or(
          criteriaBuilder.and(
              criteriaBuilder.lessThanOrEqualTo(root.join("availabilities").get("stayFromDate"), departureDate),
              criteriaBuilder.greaterThanOrEqualTo(root.join("availabilities").get("stayToDate"), departureDate)
          )
      ));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
