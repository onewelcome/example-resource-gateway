package com.onegini.examples.resourcegateway.model;

import org.apache.commons.lang3.ArrayUtils;

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
  private TokenType[] amr;

  public TokenType[] getAmr() {
    return ArrayUtils.clone(amr);
  }

  public void setAmr(final TokenType[] amr) {
    this.amr = ArrayUtils.clone(amr);
  }

}
