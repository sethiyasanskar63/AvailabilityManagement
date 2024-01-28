package com.assignment.availabilitymanagement.security;

import com.assignment.availabilitymanagement.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom UserDetails class to represent user details.
 * Author: Sanskar Sethiya
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  private static final Logger logger = LoggerFactory.getLogger(UserDetails.class);

  private String userName;
  private String password;
  private List<GrantedAuthority> authorities;

  public UserDetails(UserInfo user) {
    this.userName = user.getUsername();
    this.password = user.getPassword();
    try {
      this.authorities = Arrays.stream(user.getRoles().split(","))
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Exception while creating UserDetails", e);
      throw e;
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
