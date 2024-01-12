package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
