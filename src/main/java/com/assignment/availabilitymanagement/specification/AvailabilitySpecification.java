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
import java.util.List;

/**
 * Specification for filtering {@link Availability} records based on various criteria.
 * Supports filtering by availability ID, accommodation type ID, and date range.
 */
public class AvailabilitySpecification implements Specification<Availability> {

  private static final Logger logger = LoggerFactory.getLogger(AvailabilitySpecification.class);

  private final Long availabilityId;
  private final Long accommodationTypeId;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs a new AvailabilitySpecification with the given criteria.
   *
   * @param availabilityId      Optional ID of the availability record.
   * @param accommodationTypeId Optional ID of the associated accommodation type.
   * @param startDate           Optional start date for filtering availabilities.
   * @param endDate             Optional end date for filtering availabilities.
   */
  public AvailabilitySpecification(Long availabilityId, Long accommodationTypeId, LocalDate startDate, LocalDate endDate) {
    this.availabilityId = availabilityId;
    this.accommodationTypeId = accommodationTypeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public Predicate toPredicate(Root<Availability> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    try {
      List<Predicate> predicates = new ArrayList<>();

      if (availabilityId != null) {
        predicates.add(criteriaBuilder.equal(root.get("availabilityId"), availabilityId));
      }

      if (accommodationTypeId != null) {
        predicates.add(criteriaBuilder.equal(root.get("accommodationType").get("accommodationTypeId"), accommodationTypeId));
      }

      if (startDate != null && endDate != null) {
        Predicate overlapsWithStartDate = criteriaBuilder.lessThanOrEqualTo(root.get("stayFromDate"), endDate);
        Predicate overlapsWithEndDate = criteriaBuilder.greaterThanOrEqualTo(root.get("stayToDate"), startDate);
        predicates.add(criteriaBuilder.and(overlapsWithStartDate, overlapsWithEndDate));
      }

      predicates.add(criteriaBuilder.isNull(root.get("closingDate")));

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    } catch (Exception e) {
      logger.error("Error while building AvailabilitySpecification predicate", e);
      throw new RuntimeException("Error while building AvailabilitySpecification predicate", e);
    }
  }
}
