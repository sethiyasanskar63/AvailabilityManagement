package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
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
   * @param availabilityId Optional ID of the availability record.
   * @param accommodationTypeId Optional ID of the associated accommodation type.
   * @param startDate Optional start date for filtering availabilities.
   * @param endDate Optional end date for filtering availabilities.
   */
  public AvailabilitySpecification(Long availabilityId, Long accommodationTypeId, LocalDate startDate, LocalDate endDate) {
    this.availabilityId = availabilityId;
    this.accommodationTypeId = accommodationTypeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public Predicate toPredicate(Root<Availability> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    try {
      if (availabilityId != null) {
        predicates.add(cb.equal(root.get("availabilityId"), availabilityId));
      }

      if (accommodationTypeId != null) {
        predicates.add(cb.equal(root.join("accommodationType").get("id"), accommodationTypeId));
      }

      if (startDate != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("stayFromDate"), startDate));
      }

      if (endDate != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("stayToDate"), endDate));
      }

      if (predicates.isEmpty()) {
        logger.debug("Fetching all availabilities as no specific criteria were provided.");
      } else {
        logger.debug("Fetching availabilities with specified criteria.");
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    } catch (Exception e) {
      logger.error("Error building specification for availability query", e);
      throw new RuntimeException("Error building specification for availability query", e);
    }
  }
}
