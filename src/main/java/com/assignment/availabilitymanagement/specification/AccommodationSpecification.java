package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Accommodation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccommodationSpecification implements Specification<Accommodation> {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationSpecification.class);

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
    try {
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
    } catch (Exception e) {
      logger.error("Error while building AccommodationSpecification predicate", e);
      throw e;
    }
  }
}
