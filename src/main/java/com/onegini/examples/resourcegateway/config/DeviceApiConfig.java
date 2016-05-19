package com.onegini.examples.resourcegateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "device.api")
public class DeviceApiConfig {

  private String serverRoot;
  private String username;
  private String password;


  public String getServerRoot() {
    return serverRoot;
  }

  public void setServerRoot(final String serverRoot) {
    this.serverRoot = serverRoot;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
