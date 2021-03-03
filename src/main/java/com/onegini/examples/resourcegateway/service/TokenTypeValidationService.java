package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.onegini.examples.resourcegateway.model.TokenType;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;

@Service
public class TokenTypeValidationService {

  public void validateImplicitAuthenticationToken(final Collection<TokenType> tokenTypes) {
    if (isNoImplicitAuthenticationToken(tokenTypes)) {
      throw new InvalidAccessTokenException("Token is not an implicit authentication access token");
    }
  }

  public void validateNoImplicitAuthenticationToken(final Collection<TokenType> tokenTypes) {
    if (isImplicitAuthenticationToken(tokenTypes)) {
      throw new InvalidAccessTokenException("Token is an implicit authentication access token");
    }
  }

  private boolean isImplicitAuthenticationToken(final Collection<TokenType> tokenTypes) {
    return CollectionUtils.containsInstance(tokenTypes, IMPLICIT_AUTHENTICATION);
  }

  private boolean isNoImplicitAuthenticationToken(final Collection<TokenType> tokenTypes) {
    return !isImplicitAuthenticationToken(tokenTypes);
  }
}
