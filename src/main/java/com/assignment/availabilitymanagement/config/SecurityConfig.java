package com.assignment.availabilitymanagement.config;

import com.assignment.availabilitymanagement.security.JwtFilter;
import com.assignment.availabilitymanagement.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for security settings.
 * Author: Sanskar Sethiya
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private JwtFilter jwtFilter;

  /**
   * Configures security settings and filters.
   *
   * @param httpSecurity The HttpSecurity object to configure security.
   * @return The configured SecurityFilterChain.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    try {
      return httpSecurity.csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/auth/login", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/error")
              .permitAll()
              .anyRequest().authenticated())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authenticationProvider(authenticationProvider())
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
    } catch (Exception e) {
      logger.error("Error configuring security filter chain", e);
      throw e;
    }
  }

  /**
   * Configures the AuthenticationProvider for DaoAuthentication.
   *
   * @return The configured AuthenticationProvider.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    try {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsService());
      authenticationProvider.setPasswordEncoder(passwordEncoder());
      return authenticationProvider;
    } catch (Exception e) {
      logger.error("Error configuring authentication provider", e);
      throw e;
    }
  }

  /**
   * Configures the UserDetailsService bean.
   *
   * @return The configured UserDetailsService.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    try {
      return new UserService();
    } catch (Exception e) {
      logger.error("Error configuring user details service", e);
      throw e;
    }
  }

  /**
   * Configures the PasswordEncoder bean for password hashing.
   *
   * @return The configured PasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    try {
      return new BCryptPasswordEncoder();
    } catch (Exception e) {
      logger.error("Error configuring password encoder", e);
      throw e;
    }
  }

  /**
   * Configures the AuthenticationManager bean.
   *
   * @param config The AuthenticationConfiguration object.
   * @return The configured AuthenticationManager.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    try {
      return config.getAuthenticationManager();
    } catch (Exception e) {
      logger.error("Error configuring authentication manager", e);
      throw e;
    }
  }
}
