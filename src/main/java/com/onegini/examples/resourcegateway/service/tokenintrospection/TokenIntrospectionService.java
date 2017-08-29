package com.onegini.examples.resourcegateway.service.tokenintrospection;


import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;

@Service
public class TokenIntrospectionService {

  @Resource
  private TokenIntrospectionRequestExecutor tokenIntrospectionRequestExecutor;
  @Resource
  private TokenIntrospectionResultParser tokenIntrospectionResultParser;

  public TokenIntrospectionResult introspectAccessToken(final String accessToken) {
    final ResponseEntity<String> response = tokenIntrospectionRequestExecutor.execute(accessToken);
    return tokenIntrospectionResultParser.parse(response);
  }

}
