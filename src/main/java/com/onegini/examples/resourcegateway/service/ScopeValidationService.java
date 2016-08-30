package com.onegini.examples.resourcegateway.service;

import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.StringTokenizer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.TokenValidationResult;

@Service
public class ScopeValidationService {

  public static final String SCOPE_READ = "read";
  public static final String SCOPE_APPLICATION_DETAILS = "application-details";

  public boolean isReadScopeGranted(final String grantedScopes) {
    return isScopeGranted(grantedScopes, SCOPE_READ);
  }

  public boolean isApplicationDetailsScopeGranted(final String grantedScopes) {
    return isScopeGranted(grantedScopes, SCOPE_APPLICATION_DETAILS);
  }

  private boolean isScopeGranted(final String grantedScopes, final String scope) {
    if (StringUtils.isBlank(grantedScopes)) {
      return false;
    }

    final String[] scopes = StringUtils.split(grantedScopes, SPACE);
    return ArrayUtils.contains(scopes, scope);
  }

}
