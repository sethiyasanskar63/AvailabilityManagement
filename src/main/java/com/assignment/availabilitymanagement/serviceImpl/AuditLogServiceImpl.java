package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;
import com.assignment.availabilitymanagement.entity.AuditLog;
import com.assignment.availabilitymanagement.mapper.AuditLogMapper;
import com.assignment.availabilitymanagement.repository.AuditLogRepository;
import com.assignment.availabilitymanagement.service.AuditLogService;
import com.assignment.availabilitymanagement.specification.AuditLogSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Implementation of the AuditLogService interface, providing methods to manage audit logs.
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

  private static final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

  @Autowired
  private AuditLogRepository auditLogRepository;

  @Autowired
  private AuditLogMapper auditLogMapper;

  /**
   * Saves an audit log entry.
   *
   * @param auditLogDTO The audit log data transfer object containing the information to be saved.
   */
  @Override
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
  public void saveLogs(AuditLogDTO auditLogDTO) {
    try {
      AuditLog auditLog = auditLogMapper.toEntity(auditLogDTO);
      AuditLog savedAuditLog = auditLogRepository.saveAndFlush(auditLog);
      logger.debug("Audit log saved successfully: {}", auditLog.getLogId());
      auditLogMapper.toDto(savedAuditLog);
    } catch (Exception e) {
      logger.error("Error while saving audit log: ", e);
      throw new RuntimeException("Failed to save audit log: " + e.getMessage(), e);
    }
  }

  /**
   * Retrieves a paginated list of audit logs based on the provided criteria.
   *
   * @param auditLogId Optional ID of the specific audit log to retrieve.
   * @param startDate  Optional start date to filter the logs by their creation or event date.
   * @param endDate    Optional end date to filter the logs by their creation or event date.
   * @param pageable   {@link Pageable} object containing pagination and sorting information.
   * @return A {@link Page} of {@link AuditLogDTO} objects matching the criteria.
   */
  @Override
  @Transactional(readOnly = true)
  public Page<AuditLogDTO> getLogs(Long auditLogId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    try {
      Specification<AuditLog> specification = new AuditLogSpecification(auditLogId, startDate, endDate);
      Page<AuditLog> auditLogs = auditLogRepository.findAll(specification, pageable);
      logger.debug("Retrieved a page of audit logs based on the given criteria.");
      return auditLogs.map(auditLogMapper::toDto);
    } catch (Exception e) {
      logger.error("Error while retrieving audit logs: ", e);
      throw new RuntimeException("Failed to retrieve audit logs: " + e.getMessage(), e);
    }
  }

}
