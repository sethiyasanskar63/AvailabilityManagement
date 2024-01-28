package com.assignment.availabilitymanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * JWT Service class for token generation, validation, and extraction.
 * Author: Sanskar Sethiya
 */
@Component
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  private static final String SECRET_KEY = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";
  private static final long TOKEN_EXPIRATION_TIME_MS = 1000 * 60 * 30; // 30 minutes

  /**
   * Generate a JWT token for the given username.
   *
   * @param userName The username for which the token is generated.
   * @return The JWT token.
   */
  public String generateToken(String userName) {
    Map<String, Objects> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MS))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Extract the username from the JWT token.
   *
   * @param token The JWT token.
   * @return The extracted username.
   */
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extract the expiration date from the JWT token.
   *
   * @param token The JWT token.
   * @return The expiration date.
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validate the JWT token for the given user details.
   *
   * @param token       The JWT token.
   * @param userDetails The user details.
   * @return True if the token is valid, false otherwise.
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    try {
      final String userName = extractUserName(token);
      return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    } catch (ExpiredJwtException | MalformedJwtException e) {
      logger.error("Exception while validating JWT token", e);
      return false;
    }
  }
}
