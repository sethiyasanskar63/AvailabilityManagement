package com.assignment.availabilitymanagement.config;

import com.assignment.availabilitymanagement.entity.AuditLog;
import com.assignment.availabilitymanagement.serviceImpl.AuditLogServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@Aspect
public class AuditConfig {

  @Autowired
  private AuditLogServiceImpl auditLogService;

  @AfterReturning("execution(public * com.assignment.availabilitymanagement.serviceImpl.ResortServiceImpl.*(..))" +
      " || execution(public * com.assignment.availabilitymanagement.serviceImpl.AccommodationTypeServiceImpl.*(..))" +
      " || execution(public * com.assignment.availabilitymanagement.serviceImpl.AvailabilityServiceImpl.*(..))")
  public void logAfterReturning(JoinPoint joinPoint) {
    auditLogService.addLogs(AuditLog.builder().creationDate(new Date())
        .description(joinPoint.getTarget().getClass().getSimpleName() + " method called: " +
            joinPoint.getSignature().getName() + " with arguments: " +
            getMethodArguments(joinPoint.getArgs()))
        .build());
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
