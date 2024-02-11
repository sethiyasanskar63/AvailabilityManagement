package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing audit logs.
 * Provides functionality to add and retrieve audit log entries.
 *
 * Author: Sanskar Sethiya
 */
public interface AuditLogService {

  /**
   * Adds a new audit log entry to the system.
   *
   * @param auditLogDTO The DTO representing the details of the audit log to be added.
   */
  void saveLogs(AuditLogDTO auditLogDTO);

  /**
   * Retrieves a list of audit logs filtered by the provided parameters. Each parameter is optional;
   * when provided, they are used to filter the results. If an audit log ID is provided, it retrieves
   * a specific log entry. Date parameters are used to find logs within a specified date range.
   *
   * @param auditLogId An optional ID of the audit log to retrieve a specific log entry.
   * @param startDate The start date of the period for which logs should be retrieved; null means unbounded start date.
   * @param endDate The end date of the period for which logs should be retrieved; null means unbounded end date.
   * @return A list of {@link AuditLogDTO} objects that match the criteria.
   */
  List<AuditLogDTO> getLogs(Long auditLogId, LocalDate startDate, LocalDate endDate);
}
