package com.protectify.api.security.domain.exceptions;

public class AlertNotFoundException extends RuntimeException {
  public AlertNotFoundException(Long id) {
    super("Alert with id " + id + " not found");
  }
}