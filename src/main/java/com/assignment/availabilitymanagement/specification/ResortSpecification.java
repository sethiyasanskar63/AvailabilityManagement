package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Resort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class ResortSpecification implements Specification<Resort> {

  private static final Logger logger = LoggerFactory.getLogger(ResortSpecification.class);

  private final Long resortId;

  public ResortSpecification(Long resortId) {
    this.resortId = resortId;
  }

  @Override
  public Predicate toPredicate(Root<Resort> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    try {
      return criteriaBuilder.equal(root.get("resortId"), resortId);
    } catch (Exception e) {
      logger.error("Error while building ResortSpecification predicate", e);
      throw e;
    }
  }
}
