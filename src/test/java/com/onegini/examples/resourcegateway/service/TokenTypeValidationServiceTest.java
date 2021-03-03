package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.onegini.examples.resourcegateway.model.TokenType;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;

class TokenTypeValidationServiceTest {

  private static final String TYPE_IMPLICIT_AUTHENTICATION = "IMPLICIT_AUTHENTICATION";

  private final TokenTypeValidationService service = new TokenTypeValidationService();

  @Test
  void should_throw_exception_when_validating_implicit_authentication_token_as_no_implicit_authentication_token() {
    final EnumSet<TokenType> tokenTypes = EnumSet.of(IMPLICIT_AUTHENTICATION);
    assertThatThrownBy(() -> service.validateNoImplicitAuthenticationToken(tokenTypes))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @NullAndEmptySource
  @ParameterizedTest
  void should_validate_null_or_empty_as_no_implicit_authentication_token(final Set<TokenType> tokenTypes) {
    service.validateNoImplicitAuthenticationToken(tokenTypes);
  }

  @EnumSource(value = TokenType.class, names = { TYPE_IMPLICIT_AUTHENTICATION }, mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void should_validate_as_no_implicit_authentication_token(final TokenType tokenTypes) {
    service.validateNoImplicitAuthenticationToken(EnumSet.of(tokenTypes));
  }

  @NullAndEmptySource
  @ParameterizedTest
  void should_throw_exception_when_validating_null_or_empty_as_implicit_authentication_token(final Set<TokenType> tokenTypes) {
    assertThatThrownBy(() -> service.validateImplicitAuthenticationToken(tokenTypes))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @EnumSource(value = TokenType.class, names = { TYPE_IMPLICIT_AUTHENTICATION }, mode = EnumSource.Mode.EXCLUDE)
  @ParameterizedTest
  void should_throw_exception_when_validating_other_input_as_implicit_authentication_token(final TokenType tokenType) {
    final EnumSet<TokenType> tokenTypes = EnumSet.of(tokenType);
    assertThatThrownBy(() -> service.validateImplicitAuthenticationToken(tokenTypes))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @Test
  void should_validate_as_implicit_authentication_token() {
    service.validateImplicitAuthenticationToken(Set.of(IMPLICIT_AUTHENTICATION));
  }

}