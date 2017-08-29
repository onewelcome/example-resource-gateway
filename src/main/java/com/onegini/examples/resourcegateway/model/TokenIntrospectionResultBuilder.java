package com.onegini.examples.resourcegateway.model;

public class TokenIntrospectionResultBuilder {

  private final TokenIntrospectionResult tokenIntrospectionResult;

  public TokenIntrospectionResultBuilder() {
    tokenIntrospectionResult = new TokenIntrospectionResult();
  }

  public TokenIntrospectionResultBuilder withApplicationVersion(final String applicationVersion) {
    tokenIntrospectionResult.setApplicationVersion(applicationVersion);
    return this;
  }

  public TokenIntrospectionResultBuilder withApplicationIdentifier(final String applicationIdentifier) {
    tokenIntrospectionResult.setApplicationIdentifier(applicationIdentifier);
    return this;
  }

  public TokenIntrospectionResultBuilder withApplicationPlatform(final String applicationPlatform) {
    tokenIntrospectionResult.setApplicationPlatform(applicationPlatform);
    return this;
  }

  public TokenIntrospectionResultBuilder withUserId(final String userId) {
    tokenIntrospectionResult.setUserId(userId);
    return this;
  }

  public TokenIntrospectionResultBuilder withScopes(final String scopes) {
    tokenIntrospectionResult.setScopes(scopes);
    return this;
  }

  public TokenIntrospectionResult build() {
    return tokenIntrospectionResult;
  }
}
