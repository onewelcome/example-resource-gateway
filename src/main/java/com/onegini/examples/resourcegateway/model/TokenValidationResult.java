package com.onegini.examples.resourcegateway.model;

public class TokenValidationResult {
  private String applicationIdentifier, applicationPlatform, applicationVersion, userId, scopes;

  public String getApplicationIdentifier() {
    return applicationIdentifier;
  }

  public void setApplicationIdentifier(final String applicationIdentifier) {
    this.applicationIdentifier = applicationIdentifier;
  }

  public String getApplicationPlatform() {
    return applicationPlatform;
  }

  public void setApplicationPlatform(final String applicationPlatform) {
    this.applicationPlatform = applicationPlatform;
  }

  public String getApplicationVersion() {
    return applicationVersion;
  }

  public void setApplicationVersion(final String applicationVersion) {
    this.applicationVersion = applicationVersion;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getScopes() {
    return scopes;
  }

  public void setScopes(final String scopes) {
    this.scopes = scopes;
  }
}
