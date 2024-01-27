package com.assignment.availabilitymanagement.security;

import com.assignment.availabilitymanagement.entity.UserInfo;
import com.assignment.availabilitymanagement.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
    return userInfo.map(UserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
  }

  public String addUser(UserInfo user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userInfoRepository.save(user);
    return "User added successfully";
  }
}
