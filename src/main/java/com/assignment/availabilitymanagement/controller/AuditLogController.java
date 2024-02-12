package com.assignment.availabilitymanagement.controller;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;
import com.assignment.availabilitymanagement.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing and retrieving audit logs.
 * Provides endpoints for fetching audit log entries based on various criteria.
 */
@RestController
@RequestMapping("/api/auditLogs")
public class AuditLogController {

  private static final Logger logger = LoggerFactory.getLogger(AuditLogController.class);

  @Autowired
  private AuditLogService auditLogService;

  /**
   * Retrieves audit logs optionally filtered by audit log ID, start date, or end date.
   *
   * @param auditLogId Optional ID of the audit log for specific retrieval.
   * @param startDate  Optional start date for filtering logs from this date onwards.
   * @param endDate    Optional end date for filtering logs up to this date.
   * @return A {@link ResponseEntity} containing the list of {@link AuditLogDTO} or an appropriate error response.
   */
  @GetMapping
  public ResponseEntity<?> getAuditLogs(
      @RequestParam(required = false) Long auditLogId,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
    try {
      List<AuditLogDTO> auditLogDTOS = auditLogService.getLogs(auditLogId, startDate, endDate);
      if (auditLogDTOS.isEmpty()) {
        logger.info("No matching audit logs found for the given criteria.");
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(auditLogDTOS);
    } catch (Exception e) {
      logger.error("Error retrieving audit logs: {}", e.getMessage(), e);
      return ResponseEntity.internalServerError().body("Internal server error occurred while processing your request.");
    }
  }
}
