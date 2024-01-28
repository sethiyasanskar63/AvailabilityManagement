package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.AuditLog;

/**
 * Service interface for managing audit logs.
 * Author: Sanskar Sethiya
 */
public interface AuditLogService {

  /**
   * Add an audit log entry.
   *
   * @param auditLog Audit log entry to be added
   * @return Added audit log entry
   */
  AuditLog addLogs(AuditLog auditLog);
}
