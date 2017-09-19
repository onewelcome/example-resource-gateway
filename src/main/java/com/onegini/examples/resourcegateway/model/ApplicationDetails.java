package com.onegini.examples.resourcegateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetails {

  private String applicationIdentifier;
  private String applicationPlatform;
  private String applicationVersion;

  public ApplicationDetails(final String applicationIdentifier, final String applicationPlatform, final String applicationVersion) {
    this.applicationIdentifier = applicationIdentifier;
    this.applicationPlatform = applicationPlatform;
    this.applicationVersion = applicationVersion;
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
