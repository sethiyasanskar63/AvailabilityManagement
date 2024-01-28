package com.assignment.availabilitymanagement.security;

import com.assignment.availabilitymanagement.entity.UserInfo;
import com.assignment.availabilitymanagement.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related operations.
 * Author: Sanskar Sethiya
 */
@Service
public class UserService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Load user details by username.
   *
   * @param username Username of the user
   * @return UserDetails object
   * @throws UsernameNotFoundException if user is not found
   */
  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
      return userInfo.map(UserDetails::new)
          .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    } catch (Exception e) {
      logger.error("Error while loading user by username", e);
      throw new UsernameNotFoundException("Error loading user by username");
    }
  }

  /**
   * Add a new user.
   *
   * @param user User information
   * @return Success message
   */
  public String addUser(UserInfo user) {
    try {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userInfoRepository.save(user);
      logger.info("User added successfully: {}", user.getUsername());
      return "User added successfully";
    } catch (Exception e) {
      logger.error("Error while adding user", e);
      return "Error adding user";
    }
  }
}
