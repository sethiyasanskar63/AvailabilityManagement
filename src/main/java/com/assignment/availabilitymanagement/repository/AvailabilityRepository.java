package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Availability entity.
 * Author: Sanskar Sethiya
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>, JpaSpecificationExecutor<Availability> {

  Logger logger = LoggerFactory.getLogger(AvailabilityRepository.class);
}
