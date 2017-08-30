package com.onegini.examples.resourcegateway.model;

public class TokenIntrospectionResult {
  private boolean active;
  private String scope;
  private String clientId;
  private String tokenType;
  private int exp;
  private int iat;
  private int nbf;
  private String sub;
  private String iss;
  private int usageLimit;
  private TokenType[] amr;

  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(final String scope) {
    this.scope = scope;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(final String clientId) {
    this.clientId = clientId;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(final String tokenType) {
    this.tokenType = tokenType;
  }

  public int getExp() {
    return exp;
  }

  public void setExp(final int exp) {
    this.exp = exp;
  }

  public int getIat() {
    return iat;
  }

  public void setIat(final int iat) {
    this.iat = iat;
  }

  public int getNbf() {
    return nbf;
  }

  public void setNbf(final int nbf) {
    this.nbf = nbf;
  }

  public String getSub() {
    return sub;
  }

  public void setSub(final String sub) {
    this.sub = sub;
  }

  public String getIss() {
    return iss;
  }

  public void setIss(final String iss) {
    this.iss = iss;
  }

  public int getUsageLimit() {
    return usageLimit;
  }

  public void setUsageLimit(final int usageLimit) {
    this.usageLimit = usageLimit;
  }

  public TokenType[] getAmr() {
    return amr;
  }

  public void setAmr(final TokenType[] amr) {
    this.amr = amr;
  }
}
