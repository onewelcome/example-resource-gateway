package com.onegini.examples.resourcegateway.model;

public class TokenValidationResultBuilder {

  private final TokenValidationResult tokenValidationResult;

  public  TokenValidationResultBuilder() {
    tokenValidationResult = new TokenValidationResult();
  }

  public TokenValidationResultBuilder withApplicationVersion(final String applicationVersion) {
    tokenValidationResult.setApplicationVersion(applicationVersion);
    return this;
  }

  public TokenValidationResultBuilder withApplicationIdentifier(final String applicationIdentifier) {
    tokenValidationResult.setApplicationIdentifier(applicationIdentifier);
    return this;
  }

  public TokenValidationResultBuilder withApplicationPlatform(final String applicationPlatform) {
    tokenValidationResult.setApplicationPlatform(applicationPlatform);
    return this;
  }

  public TokenValidationResultBuilder withUserId(final String userId) {
    tokenValidationResult.setUserId(userId);
    return this;
  }

  public TokenValidationResultBuilder withScopes(final String scopes) {
    tokenValidationResult.setScopes(scopes);
    return this;
  }

  public TokenValidationResult build() {
    return tokenValidationResult;
  }
}
