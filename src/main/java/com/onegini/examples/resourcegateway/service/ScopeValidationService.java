package com.onegini.examples.resourcegateway.service;

import static org.apache.commons.lang3.StringUtils.SPACE;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.exception.ScopeNotGrantedException;

@Service
public class ScopeValidationService {

  public static final String SCOPE_READ = "read";
  public static final String SCOPE_APPLICATION_DETAILS = "application-details";

  public void validateReadScopeGranted(final String grantedScopes) {
    validateScopeGranted(grantedScopes, SCOPE_READ);
  }

  public void validateApplicationDetailsScopeGranted(final String grantedScopes) {
    validateScopeGranted(grantedScopes, SCOPE_APPLICATION_DETAILS);
  }

  private void validateScopeGranted(final String grantedScopes, final String scope) {
    if (StringUtils.isBlank(grantedScopes)) {
      throw new ScopeNotGrantedException("No scopes granted to access token");
    }

    final String[] scopes = StringUtils.split(grantedScopes, SPACE);
    final boolean scopeNotGranted = !ArrayUtils.contains(scopes, scope);
    if(scopeNotGranted) {
      final String message = String.format("Scope %s not granted to provided access token.", scope);
      throw new ScopeNotGrantedException(message);
    }
  }

}
