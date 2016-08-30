package com.onegini.examples.resourcegateway.model.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(final String message) {
    super(message);
  }
}
