package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Captures audit log entries for tracking changes or significant events within the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "log_id")
  private long logId;

  @Column(name = "description", nullable = false)
  private String description;

  @CreationTimestamp
  @Column(name = "creation_date", nullable = false)
  private Date creationDate;
}
