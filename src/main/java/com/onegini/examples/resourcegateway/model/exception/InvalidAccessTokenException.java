package com.onegini.examples.resourcegateway.model.exception;

public class InvalidAccessTokenException extends RuntimeException {

  public InvalidAccessTokenException(final String message) {
    super(message);
  }

}
