package com.onegini.examples.resourcegateway.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {

  private String id;
  private String name;
  private String application;
  private String platform;
  private Long createdAt;
  private Date lastLogin;
  private Set<String> tokenTypes;
  private boolean mobileAuthenticationEnabled;

  public Device() {
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(final String application) {
    this.application = application;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(final String platform) {
    this.platform = platform;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final Long createdAt) {
    this.createdAt = createdAt;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(final Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Set<String> getTokenTypes() {
    return tokenTypes;
  }

  public void setTokenTypes(final Set<String> tokenTypes) {
    this.tokenTypes = tokenTypes;
  }

  public boolean isMobileAuthenticationEnabled() {
    return mobileAuthenticationEnabled;
  }

  public void setMobileAuthenticationEnabled(final boolean mobileAuthenticationEnabled) {
    this.mobileAuthenticationEnabled = mobileAuthenticationEnabled;
  }
}
