package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;

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
    return CollectionUtils.containsInstance(amrs, IMPLICIT_AUTHENTICATION.name());
  }

  private boolean isNoImplicitAuthenticationToken(final Collection<String> tokenTypes) {
    return !isImplicitAuthenticationToken(tokenTypes);
  }
}
