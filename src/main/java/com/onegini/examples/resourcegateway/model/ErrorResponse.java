package com.onegini.examples.resourcegateway.model;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

  private final String error;
  private final String errorDescription;

  public ErrorResponse(final String error, final String errorDescription) {
    this.error = error;
    this.errorDescription = errorDescription;
  }

  public String getError() {
    return error;
  }

  public String getErrorDescription() {
    return errorDescription;
  }
}
