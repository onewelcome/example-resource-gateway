package com.onegini.examples.resourcegateway.model;

public class TokenValidationResult {
  private final String userId, scopes;

  public TokenValidationResult(final String userId, final String scopes) {
    this.userId = userId;
    this.scopes = scopes;
  }

  public String getUserId() {
    return userId;
  }

  public String getScopes() {
    return scopes;
  }
}
