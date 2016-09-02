package com.onegini.examples.resourcegateway.model.exception;

public class ScopeNotGrantedException extends RuntimeException {

  public ScopeNotGrantedException(final String message) {
    super(message);
  }

}
