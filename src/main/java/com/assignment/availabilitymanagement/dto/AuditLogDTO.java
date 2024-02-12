package com.assignment.availabilitymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for AuditLog data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDTO {
  private long logId;
  private String description;
  private Date creationDate;
}
