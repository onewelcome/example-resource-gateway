package com.onegini.examples.resourcegateway.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetails {

  private static final String SEPARATOR = " ";
  private static final int APP_ID_INDEX = 0;
  private static final int APP_PLATFORM_INDEX = 1;
  private static final int APP_VERSION_INDEX = 2;

  private String applicationIdentifier;
  private String applicationPlatform;
  private String applicationVersion;

  public static ApplicationDetails fromIssuer(final String issuer) {
    final String[] details = StringUtils.split(issuer, SEPARATOR);
    final ApplicationDetails applicationDetails = new ApplicationDetails();

    applicationDetails.setApplicationIdentifier(details[APP_ID_INDEX]);
    applicationDetails.setApplicationPlatform(details[APP_PLATFORM_INDEX]);
    applicationDetails.setApplicationVersion(details[APP_VERSION_INDEX]);

    return applicationDetails;
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
