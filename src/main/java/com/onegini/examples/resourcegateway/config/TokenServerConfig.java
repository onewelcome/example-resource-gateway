package com.onegini.examples.resourcegateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resource.gateway.tokenServer")
public class TokenServerConfig {

  private String clientId;
  private String clientSecret;
  private String baseUri;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(final String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(final String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getBaseUri() {
    return baseUri;
  }

  public void setBaseUri(final String baseUri) {
    this.baseUri = baseUri;
  }
}
