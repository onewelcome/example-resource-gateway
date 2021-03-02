package com.onegini.examples.resourcegateway.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.exception.ScopeNotGrantedException;

@Service
public class ScopeValidationService {

  public static final String SCOPE_READ = "read";
  public static final String SCOPE_APPLICATION_DETAILS = "application-details";
  public static final String SCOPE_WRITE = "write";
  private static final String SCOPE_SEPARATOR = " ";

  public void validateScopeGranted(final String grantedScopes, final String requiredScope) {
    if (StringUtils.isBlank(grantedScopes)) {
      throw new ScopeNotGrantedException("No scopes granted to access token");
    }

    final String[] scopes = StringUtils.split(grantedScopes, SCOPE_SEPARATOR);
    final boolean scopeNotGranted = !ArrayUtils.contains(scopes, requiredScope);
    if (scopeNotGranted) {
      final String message = String.format("Scope %s not granted to provided access token.", requiredScope);
      throw new ScopeNotGrantedException(message);
    }
  }
}
