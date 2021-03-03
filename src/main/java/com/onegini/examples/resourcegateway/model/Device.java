package com.onegini.examples.resourcegateway.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {

  private String id;
  private String name;
  private String application;
  private String model;
  private String platform;
  private String osVersion;
  private Long createdAt;
  private Date lastLogin;
  private Set<String> tokenTypes;
  private boolean mobileAuthenticationEnabled;
  private boolean pushAuthenticationEnabled;

}
