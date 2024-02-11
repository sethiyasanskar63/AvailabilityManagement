package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;
import com.assignment.availabilitymanagement.entity.AuditLog;
import org.mapstruct.Mapper;

/**
 * Maps AuditLog entities to DTOs.
 */
@Mapper(componentModel = "spring")
public interface AuditLogMapper {

  AuditLogDTO toDto(AuditLog auditLog);

  AuditLog toEntity(AuditLogDTO dto);
}
