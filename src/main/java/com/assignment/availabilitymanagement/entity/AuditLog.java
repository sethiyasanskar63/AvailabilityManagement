package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Entity class representing AuditLog.
 * Author: Sanskar Sethiya
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

  @Column(name = "description")
  private String description;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "creation_date", nullable = false, updatable = false)
  private Date creationDate;
}
