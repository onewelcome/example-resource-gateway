package com.onegini.examples.resourcegateway.service.tokenvalidation;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onegini.examples.resourcegateway.model.TokenValidationResult;
import com.onegini.examples.resourcegateway.model.TokenValidationResultBuilder;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import com.onegini.examples.resourcegateway.model.exception.TokenServerException;

@Service
public class TokenValidationResultParser {

  @Resource
  private ObjectMapper objectMapper;

  public TokenValidationResult parse(final ResponseEntity<String> response) {

    if (response.getStatusCode() != HttpStatus.OK) {
      throw new InvalidAccessTokenException("Token validation request return status code: " + response.getStatusCode());
    }

    final Map<String, Object> jsonMap = mapJsonResponse(response.getBody());

    final String userId = (String) jsonMap.get("reference_id");
    final String scopes = (String) jsonMap.get("scope");
    final String applicationVersion = (String) jsonMap.get("app_version");
    final String applicationIdentifier = (String) jsonMap.get("app_identifier");
    final String applicationPlatform = (String) jsonMap.get("app_platform");

    return new TokenValidationResultBuilder()
        .withUserId(userId)
        .withScopes(scopes)
        .withApplicationVersion(applicationVersion)
        .withApplicationIdentifier(applicationIdentifier)
        .withApplicationPlatform(applicationPlatform)
        .build();
  }

  private Map<String, Object> mapJsonResponse(final String responseBody) {
    final Map<String, Object> jsonMap;
    final TypeReference<Map<String,Object>> mapTypeReference = new TypeReference<Map<String,Object>>() {};
    try {
      jsonMap = objectMapper.readValue(responseBody, mapTypeReference);
    } catch (final IOException exception) {
      throw new TokenServerException(exception);
    }
    return jsonMap;
  }

}
