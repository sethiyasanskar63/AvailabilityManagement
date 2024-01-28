package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.AccommodationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for AccommodationType entity.
 * Author: Sanskar Sethiya
 */
@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long>, JpaSpecificationExecutor<AccommodationType> {

  Logger logger = LoggerFactory.getLogger(AccommodationTypeRepository.class);
}
