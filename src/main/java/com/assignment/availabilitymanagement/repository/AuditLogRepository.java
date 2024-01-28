package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for AuditLog entity.
 * Author: Sanskar Sethiya
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

  Logger logger = LoggerFactory.getLogger(AuditLogRepository.class);
}
