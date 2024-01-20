package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long>, JpaSpecificationExecutor<Resort> {
}
