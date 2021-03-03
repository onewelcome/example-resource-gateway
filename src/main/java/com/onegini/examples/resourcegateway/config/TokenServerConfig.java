package com.onegini.examples.resourcegateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "resource.gateway.token-server")
public class TokenServerConfig {

  private String clientId;
  private String clientSecret;
  private String baseUri;

}
