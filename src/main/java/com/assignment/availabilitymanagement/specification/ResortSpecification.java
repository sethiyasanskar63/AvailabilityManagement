package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Resort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification class for filtering Resort entities.
 * Author: Sanskar Sethiya
 */
public class ResortSpecification implements Specification<Resort> {

  private static final Logger logger = LoggerFactory.getLogger(ResortSpecification.class);

  private final Long resortId;

  /**
   * Constructor for creating a ResortSpecification.
   *
   * @param resortId ID of the resort for filtering
   */
  public ResortSpecification(Long resortId) {
    this.resortId = resortId;
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
  public Predicate toPredicate(Root<Resort> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    try {
      if (resortId != null) {
        return criteriaBuilder.equal(root.get("resortId"), resortId);
      } else {
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
      }
    } catch (Exception e) {
      logger.error("Error while building ResortSpecification predicate", e);
      throw new RuntimeException("Error while building ResortSpecification predicate", e);
    }
  }
}
