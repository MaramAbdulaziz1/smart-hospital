package com.team10.smarthospital.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  private final JdbcTemplate jdbcTemplate;

  public HealthController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Simple runtime DB check: tries "SELECT 1".
   * On success returns HTTP 200 with a plain-text message.
   */
  @GetMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> testDatabase() {
    try {
      Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
      if (one != null && one == 1) {
        return ResponseEntity.ok("Database connection is working!");
      }
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Database connection failed: unexpected result");
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Database connection failed: " + ex.getMessage());
    }
  }
}
