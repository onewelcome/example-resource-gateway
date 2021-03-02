package com.onegini.examples.resourcegateway.service.tokenintrospection;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;

@Service
public class TokenIntrospectionService {

  @Resource
  private TokenIntrospectionRequestExecutor tokenIntrospectionRequestExecutor;

  public TokenIntrospectionResult introspectAccessToken(final String accessToken) {
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionRequestExecutor.execute(accessToken).getBody();
    final boolean tokenInvalid = tokenIntrospectionResult == null || !tokenIntrospectionResult.isActive();

    if (tokenInvalid) {
      throw new InvalidAccessTokenException("Token introspection result: invalid token");
    }

    return tokenIntrospectionResult;
  }

}
