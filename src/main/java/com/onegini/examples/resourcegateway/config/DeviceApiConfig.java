package com.onegini.examples.resourcegateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "device.api")
public class DeviceApiConfig {

  private String serverRoot;
  private String username;
  private String password;

}
