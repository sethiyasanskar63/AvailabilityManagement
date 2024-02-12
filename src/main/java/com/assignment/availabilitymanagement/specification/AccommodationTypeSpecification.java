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
 * Specification for querying AccommodationType entities.
 * Supports filtering by accommodation type ID and/or availability within a specific date range.
 */
public class AccommodationTypeSpecification implements Specification<AccommodationType> {

  private static final Logger logger = LoggerFactory.getLogger(AccommodationTypeSpecification.class);

  private final Long accommodationTypeId;
  private final LocalDate arrivalDate;
  private final LocalDate departureDate;

  /**
   * Constructs a new AccommodationTypeSpecification with the specified filters.
   *
   * @param accommodationTypeId The ID of the accommodation type to filter by (nullable).
   * @param arrivalDate         The start date of the availability period to filter by (nullable).
   * @param departureDate       The end date of the availability period to filter by (nullable).
   */
  public AccommodationTypeSpecification(Long accommodationTypeId, LocalDate arrivalDate, LocalDate departureDate) {
    this.accommodationTypeId = accommodationTypeId;
    this.arrivalDate = arrivalDate;
    this.departureDate = departureDate;
  }

  /**
   * Builds the predicate for querying accommodation types based on the specified filters.
   *
   * @param root  The root of the query.
   * @param query The criteria query.
   * @param cb    The criteria builder.
   * @return The predicate for filtering the query, based on accommodation type ID and availability dates.
   */
  @Override
  public Predicate toPredicate(Root<AccommodationType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    try {
      if (accommodationTypeId != null) {
        predicates.add(cb.equal(root.get("id"), accommodationTypeId));
      }
      if (arrivalDate != null && departureDate != null) {
        if (!arrivalDate.isAfter(departureDate)) {
          Predicate arrivalBeforeDeparture = cb.lessThanOrEqualTo(root.join("availabilities").get("stayFromDate"), departureDate);
          Predicate departureAfterArrival = cb.greaterThanOrEqualTo(root.join("availabilities").get("stayToDate"), arrivalDate);
          predicates.add(cb.and(arrivalBeforeDeparture, departureAfterArrival));
        } else {
          logger.warn("Arrival date is after departure date for accommodationTypeId: {}", accommodationTypeId);
        }
      }
    } catch (Exception e) {
      logger.error("Error building predicates in AccommodationTypeSpecification for accommodationTypeId: {}", accommodationTypeId, e);
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
