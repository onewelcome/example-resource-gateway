package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;

import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TokenTypeValidationService {

  public void validateImplicitAuthenticationToken(final Collection<String> amrs) {
    if (isNoImplicitAuthenticationToken(amrs)) {
      throw new InvalidAccessTokenException("Token is not an implicit authentication access token");
    }
  }

  public void validateNoImplicitAuthenticationToken(final Collection<String> amrs) {
    if (isImplicitAuthenticationToken(amrs)) {
      throw new InvalidAccessTokenException("Token is an implicit authentication access token");
    }
  }

  private boolean isImplicitAuthenticationToken(final Collection<String> amrs) {
    return amrs != null && amrs.contains(IMPLICIT_AUTHENTICATION.name());
  }

  private boolean isNoImplicitAuthenticationToken(final Collection<String> tokenTypes) {
    return !isImplicitAuthenticationToken(tokenTypes);
  }
}
