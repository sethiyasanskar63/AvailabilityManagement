package com.assignment.availabilitymanagement.specification;

import com.assignment.availabilitymanagement.entity.Resort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Specifications for querying Resort entities.
 */
public class ResortSpecification {

  /**
   * Specification to filter resorts by their ID.
   *
   * @param resortId The ID of the resort to filter by.
   * @return A Specification for the Resort entity.
   */
  public static Specification<Resort> hasResortId(Long resortId) {
    return new Specification<Resort>() {
      @Override
      public Predicate toPredicate(Root<Resort> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (resortId == null) {
          return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.equal(root.get("resortId"), resortId);
      }
    };
  }
}
