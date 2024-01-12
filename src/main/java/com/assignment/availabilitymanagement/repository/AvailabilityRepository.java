package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
