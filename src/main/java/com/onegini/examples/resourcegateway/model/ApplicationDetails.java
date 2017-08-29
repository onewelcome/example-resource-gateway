package com.onegini.examples.resourcegateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetails {

  private String applicationIdentifier;
  private String applicationPlatform;
  private String applicationVersion;

  public ApplicationDetails(final TokenIntrospectionResult tokenIntrospectionResult) {
    setApplicationIdentifier(tokenIntrospectionResult.getApplicationIdentifier());
    setApplicationPlatform(tokenIntrospectionResult.getApplicationPlatform());
    setApplicationVersion(tokenIntrospectionResult.getApplicationVersion());
  }

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
}
