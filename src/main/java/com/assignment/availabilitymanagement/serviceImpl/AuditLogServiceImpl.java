package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.AuditLog;
import com.assignment.availabilitymanagement.repository.AuditLogRepository;
import com.assignment.availabilitymanagement.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing audit logs.
 * Author: Sanskar Sethiya
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

  private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

  @Autowired
  private AuditLogRepository auditLogRepository;

  /**
   * Add an audit log entry.
   *
   * @param auditLog Audit log entry to be added
   * @return Saved audit log entry
   * @throws RuntimeException if there is an error while saving data to the database
   */
  @Override
  public AuditLog addLogs(AuditLog auditLog) {
    try {
      return auditLogRepository.saveAndFlush(auditLog);
    } catch (Exception e) {
      logger.error("Error while saving log", e);
      throw new RuntimeException("Error while saving log", e);
    }
  }
}
