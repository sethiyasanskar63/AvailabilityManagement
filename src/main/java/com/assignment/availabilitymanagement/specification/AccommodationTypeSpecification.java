package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.AccommodationType;
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

public class AccommodationTypeSpecification implements Specification<AccommodationType> {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeSpecification.class);

  private final Long accommodationTypeId;
  private final LocalDate arrivalDate;
  private final LocalDate departureDate;

  public AccommodationTypeSpecification(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    this.accommodationTypeId = accommodationTypeId;
    this.arrivalDate = arrivalDate;
    this.departureDate = departureDate;
  }

  @Override
  public Predicate toPredicate(Root<AccommodationType> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    try {
      List<Predicate> predicates = new ArrayList<>();

      if (accommodationTypeId != null) {
        predicates.add(criteriaBuilder.equal(root.get("accommodationTypeId"), accommodationTypeId));
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
      logger.error("Error while building AccommodationTypeSpecification predicate", e);
      throw e;
    }
  }
}
