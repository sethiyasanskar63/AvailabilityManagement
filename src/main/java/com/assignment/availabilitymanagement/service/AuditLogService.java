package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing audit logs.
 * Provides functionality to add and retrieve audit log entries.
 * <p>
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
   * Retrieves a paginated list of audit logs filtered by the provided parameters. Each parameter is optional
   * and used to filter the results. If an audit log ID is specified, it retrieves a specific log entry.
   * Date parameters are utilized to find logs within the specified date range. Pagination parameters determine
   * the page of results to return along with the number of records per page.
   *
   * @param auditLogId An optional ID of the audit log to retrieve a specific log entry.
   * @param startDate  The start date of the period for which logs should be retrieved; null indicates an unbounded start date.
   * @param endDate    The end date of the period for which logs should be retrieved; null indicates an unbounded end date.
   * @param pageable   {@link Pageable} instance containing pagination and sorting information.
   * @return A {@link Page} of {@link AuditLogDTO} objects that match the criteria, along with pagination information.
   */
  Page<AuditLogDTO> getLogs(Long auditLogId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
