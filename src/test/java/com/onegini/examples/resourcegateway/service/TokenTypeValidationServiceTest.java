package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.model.TokenType.IMPLICIT_AUTHENTICATION;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

class TokenTypeValidationServiceTest {

  private static final String TYPE_IMPLICIT_AUTHENTICATION = "IMPLICIT_AUTHENTICATION";

  private final TokenTypeValidationService service = new TokenTypeValidationService();

  @Test
  void should_throw_exception_when_validating_implicit_authentication_token_as_no_implicit_authentication_token() {
    final Set<String> amrs = Set.of(IMPLICIT_AUTHENTICATION.name());
    assertThatThrownBy(() -> service.validateNoImplicitAuthenticationToken(amrs))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @NullAndEmptySource
  @ParameterizedTest
  void should_validate_null_or_empty_as_no_implicit_authentication_token(final Set<String> tokenTypes) {
    service.validateNoImplicitAuthenticationToken(tokenTypes);
  }

  @ValueSource(strings = TYPE_IMPLICIT_AUTHENTICATION)
  @ParameterizedTest
  void should_validate_as_no_implicit_authentication_token(final String tokenTypes) {
    service.validateNoImplicitAuthenticationToken(Set.of(tokenTypes));
  }

  @NullAndEmptySource
  @ParameterizedTest
  void should_throw_exception_when_validating_null_or_empty_as_implicit_authentication_token(final Set<String> tokenTypes) {
    assertThatThrownBy(() -> service.validateImplicitAuthenticationToken(tokenTypes))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @ValueSource(strings = TYPE_IMPLICIT_AUTHENTICATION)
  @ParameterizedTest
  void should_throw_exception_when_validating_other_input_as_implicit_authentication_token(final String tokenType) {
    final Set<String> tokenTypes = Set.of(tokenType);
    assertThatThrownBy(() -> service.validateImplicitAuthenticationToken(tokenTypes))
        .isInstanceOf(InvalidAccessTokenException.class);
  }

  @Test
  void should_validate_as_implicit_authentication_token() {
    service.validateImplicitAuthenticationToken(Set.of(IMPLICIT_AUTHENTICATION.name()));
  }

}