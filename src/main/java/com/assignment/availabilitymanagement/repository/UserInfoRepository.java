package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByUsername(String userName);
}
