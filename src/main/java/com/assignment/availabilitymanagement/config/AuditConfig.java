package com.assignment.availabilitymanagement.config;

import com.assignment.availabilitymanagement.dto.AuditLogDTO;
import com.assignment.availabilitymanagement.serviceImpl.AuditLogServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * AOP Configuration for auditing method calls.
 * Author: Sanskar Sethiya
 */
@Configuration
@Aspect
public class AuditConfig {

  private static final Logger logger = LoggerFactory.getLogger(AuditConfig.class);

  @Autowired
  private AuditLogServiceImpl auditLogService;

  /**
   * Logs information after successful execution of service methods related to resorts, accommodation types,
   * and availabilities.
   *
   * @param joinPoint The join point at which the advice is applied.
   */
  @AfterReturning("execution(public * com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl.*(..))" +
      " || execution(public * com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl.*(..))" +
      " || execution(public * com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl.*(..))")
  public void logAfterReturning(JoinPoint joinPoint) {
    try {
      auditLogService.saveLogs(AuditLogDTO.builder().creationDate(new Date())
          .description(joinPoint.getTarget().getClass().getSimpleName() + " method called: " +
              joinPoint.getSignature().getName() + " with arguments: " +
              getMethodArguments(joinPoint.getArgs()))
          .build());
    } catch (Exception e) {
      logger.error("Error logging after method execution", e);
    }
  }

  private String getMethodArguments(Object[] args) {
    StringBuilder arguments = new StringBuilder();
    for (Object arg : args) {
      arguments.append(arg).append(", ");
    }
    if (arguments.length() > 2) {
      arguments.setLength(arguments.length() - 2);
    }
    return arguments.toString();
  }
}
