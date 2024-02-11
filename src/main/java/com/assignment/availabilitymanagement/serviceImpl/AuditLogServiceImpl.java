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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
      AuditLog savedAuditLog = auditLogRepository.save(auditLog);
      logger.debug("Audit log saved successfully: {}", auditLog.getLogId());
      auditLogMapper.toDto(savedAuditLog);
    } catch (Exception e) {
      logger.error("Error while saving audit log: ", e);
      throw new RuntimeException("Failed to save audit log: " + e.getMessage(), e);
    }
  }

  /**
   * Retrieves audit logs based on the given criteria.
   *
   * @param auditLogId Optional ID of the audit log for specific retrieval.
   * @param startDate Optional start date for filtering logs from this date.
   * @param endDate Optional end date for filtering logs up to this date.
   * @return A list of audit log entries that match the criteria.
   */
  @Override
  @Transactional(readOnly = true)
  public List<AuditLogDTO> getLogs(Long auditLogId, LocalDate startDate, LocalDate endDate) {
    try {
      Specification<AuditLog> specification = new AuditLogSpecification(auditLogId, startDate, endDate);
      List<AuditLog> auditLogs = auditLogRepository.findAll(specification);
      logger.debug("Retrieved {} audit logs based on the given criteria.", auditLogs.size());
      return auditLogs.stream()
          .map(auditLogMapper::toDto)
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Error while retrieving audit logs: ", e);
      throw new RuntimeException("Failed to retrieve audit logs: " + e.getMessage(), e);
    }
  }
}
