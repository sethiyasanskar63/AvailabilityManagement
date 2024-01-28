package com.assignment.availabilitymanagement.repository;

import com.assignment.availabilitymanagement.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserInfo entity.
 * Author: Sanskar Sethiya
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Logger logger = LoggerFactory.getLogger(UserInfoRepository.class);

  /**
   * Find user information by username.
   *
   * @param userName The username to search for.
   * @return An optional containing user information if found.
   */
  Optional<UserInfo> findByUsername(String userName);
}
