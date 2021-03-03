package com.onegini.examples.resourcegateway.model;

import java.util.Collection;

import lombok.Data;

@Data
public class TokenIntrospectionResult {
  private boolean active;
  private String scope;
  private String clientId;
  private String tokenType;
  private int exp;
  private int iat;
  private int nbf;
  private String appIdentifier;
  private String appVersion;
  private String appPlatform;
  private String sub;
  private int usageLimit;
  private Collection<TokenType> amr;

}
