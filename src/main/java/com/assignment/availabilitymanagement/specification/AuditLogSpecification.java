package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.AuditLog;
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
 * Specification for querying AuditLog entities.
 * Supports filtering by audit log ID, and/or a date range (start and end dates).
 */
public class AuditLogSpecification implements Specification<AuditLog> {

  private static final Logger logger = LoggerFactory.getLogger(AuditLogSpecification.class);

  private final Long auditLogId;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs a new AuditLogSpecification with the specified filters.
   *
   * @param auditLogId The ID of the audit log to filter by (nullable).
   * @param startDate  The start date of the range to filter by (nullable).
   * @param endDate    The end date of the range to filter by (nullable).
   */
  public AuditLogSpecification(Long auditLogId, LocalDate startDate, LocalDate endDate) {
    this.auditLogId = auditLogId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Builds the predicate for querying audit logs based on the specified filters.
   *
   * @param root  The root of the query.
   * @param query The criteria query.
   * @param cb    The criteria builder.
   * @return The predicate for filtering the query.
   */
  @Override
  public Predicate toPredicate(Root<AuditLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    try {
      if (auditLogId != null) {
        predicates.add(cb.equal(root.get("logId"), auditLogId));
      }
      if (startDate != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.<LocalDate>get("creationDate"), startDate));
      }
      if (endDate != null) {
        predicates.add(cb.lessThanOrEqualTo(root.<LocalDate>get("creationDate"), endDate));
      }
    } catch (Exception e) {
      logger.error("Error building predicates in AuditLogSpecification for auditLogId: {}", auditLogId, e);
    }
    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
