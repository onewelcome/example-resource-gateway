package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.service.ScopeValidationService.SCOPE_READ;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.onegini.examples.resourcegateway.model.exception.ScopeNotGrantedException;

class ScopeValidationServiceTest {

  private final ScopeValidationService service = new ScopeValidationService();

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = { "openid" })
  void should_throw_exception_when_read_scope_not_granted(final String scopes) {
    assertThatThrownBy(() -> service.validateScopeGranted(scopes, SCOPE_READ))
        .isInstanceOf(ScopeNotGrantedException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = { SCOPE_READ, SCOPE_READ + " openid", "openid " + SCOPE_READ })
  void should_grant_read_scope(final String scopes) {
    service.validateScopeGranted(scopes, SCOPE_READ);
  }

}