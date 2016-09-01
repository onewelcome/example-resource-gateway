package com.onegini.examples.resourcegateway.model.exception;

public class NoAccessTokenProvidedException extends RuntimeException {

  public NoAccessTokenProvidedException(final String message) {
    super(message);
  }

}
