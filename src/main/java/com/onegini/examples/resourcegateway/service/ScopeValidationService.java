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

  public boolean isReadScopeGranted(final String grantedScopes) {
    if (StringUtils.isBlank(grantedScopes)) {
      return false;
    }

    final String[] scopes = StringUtils.split(grantedScopes, SPACE);
    return ArrayUtils.contains(scopes, SCOPE_READ);
  }

}
