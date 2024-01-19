package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Availability;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvailabilitySpecification implements Specification<Availability> {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilitySpecification.class);
  private final Long availabilityId;
  private final Long accommodationId;
  private final Long accommodationTypeId;
  private final LocalDate arrivalDate;
  private final LocalDate departureDate;

  public AvailabilitySpecification(Long availabilityId, Long accommodationId, Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    this.availabilityId = availabilityId;
    this.accommodationId = accommodationId;
    this.accommodationTypeId = accommodationTypeId;
    this.arrivalDate = arrivalDate;
    this.departureDate = departureDate;
  }

  @Override
  public Predicate toPredicate(Root<Availability> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();
    long noOfDays = -1;
    if (arrivalDate != null && departureDate != null) {
      noOfDays = ChronoUnit.DAYS.between(arrivalDate, departureDate);
    }
    logger.info(String.valueOf(noOfDays));

    if (availabilityId != null) {
      predicates.add(criteriaBuilder.equal(root.get("availabilityId"), availabilityId));
    }

    if (accommodationId != null) {
      predicates.add(criteriaBuilder.equal(root.get("accommodation").get("accommodationId"), accommodationId));
    }

    if (accommodationTypeId != null) {
      predicates.add(criteriaBuilder.equal(root.get("accommodationType").get("accommodationTypeId"), accommodationTypeId));
    }

    if (noOfDays != -1) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minNight"), noOfDays));
    }

    if (arrivalDate != null) {
      predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("stayFromDate"), arrivalDate),
          criteriaBuilder.greaterThanOrEqualTo(root.get("stayToDate"), arrivalDate)));
    }

    if (departureDate != null) {
      predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("stayFromDate"), departureDate),
          criteriaBuilder.greaterThanOrEqualTo(root.get("stayToDate"), departureDate)));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
