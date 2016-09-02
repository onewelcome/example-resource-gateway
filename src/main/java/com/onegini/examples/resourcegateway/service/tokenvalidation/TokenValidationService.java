package com.onegini.examples.resourcegateway.service.tokenvalidation;


import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.TokenValidationResult;

@Service
public class TokenValidationService {

  @Resource
  private TokenValidationRequestExecutor tokenValidationRequestExecutor;
  @Resource
  private TokenValidationResultParser tokenValidationResultParser;

  public TokenValidationResult validateAccessToken(final String accessToken) {
    final ResponseEntity<String> response = tokenValidationRequestExecutor.execute(accessToken);
    return tokenValidationResultParser.parse(response);
  }

}
