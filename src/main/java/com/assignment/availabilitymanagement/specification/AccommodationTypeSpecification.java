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

/**
 * Specification class for filtering AccommodationType entities.
 * Author: Sanskar Sethiya
 */
public class AccommodationTypeSpecification implements Specification<AccommodationType> {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeSpecification.class);

  private final Long accommodationTypeId;
  private final LocalDate arrivalDate;
  private final LocalDate departureDate;

  /**
   * Constructor for creating an AccommodationTypeSpecification.
   *
   * @param accommodationTypeId ID of the accommodation type
   * @param arrivalDate         Arrival date for filtering
   * @param departureDate       Departure date for filtering
   */
  public AccommodationTypeSpecification(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    this.accommodationTypeId = accommodationTypeId;
    this.arrivalDate = arrivalDate;
    this.departureDate = departureDate;
  }

  /**
   * Build the predicate based on the specified criteria.
   *
   * @param root             Root entity
   * @param query            Criteria query
   * @param criteriaBuilder Criteria builder
   * @return Predicate representing the filtering criteria
   * @throws RuntimeException if there is an error while building the predicate
   */
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
      throw new RuntimeException("Error while building AccommodationTypeSpecification predicate", e);
    }
  }
}
