package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.Resort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Resort entity.
 * Author: Sanskar Sethiya
 */
@Repository
public interface ResortRepository extends JpaRepository<Resort, Long>, JpaSpecificationExecutor<Resort> {

  Logger logger = LoggerFactory.getLogger(ResortRepository.class);
}
