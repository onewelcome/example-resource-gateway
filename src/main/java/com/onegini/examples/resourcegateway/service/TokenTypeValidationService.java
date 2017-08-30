package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.TokenType;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;

@Service
public class TokenTypeValidationService {

  public void validateImplicitAuthenticationToken(final TokenType[] tokenTypes) {
    if (isNoImplicitAuthenticationToken(tokenTypes)) {
      throw new InvalidAccessTokenException("Token is not an implicit authentication access token");
    }
  }

  public void validateNoImplicitAuthenticationToken(final TokenType[] tokenTypes) {
    if (isImplicitAuthenticationToken(tokenTypes)) {
      throw new InvalidAccessTokenException("Token is an implicit authentication access token");
    }
  }

  private boolean isImplicitAuthenticationToken(final TokenType[] tokenTypes) {
    return ArrayUtils.contains(tokenTypes, IMPLICIT_AUTHENTICATION);
  }

  private boolean isNoImplicitAuthenticationToken(final TokenType[] tokenTypes) {
    return !isImplicitAuthenticationToken(tokenTypes);
  }
}
