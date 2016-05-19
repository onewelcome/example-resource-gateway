package com.onegini.examples.resourcegateway.service;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onegini.examples.resourcegateway.config.TokenServerConfig;
import com.onegini.examples.resourcegateway.model.TokenValidationResult;
import com.onegini.examples.resourcegateway.util.BasicAuthenticationHeaderBuilder;

@Service
public class TokenValidationService {

  private static final String GRANT_TYPE = "grant_type";
  private static final String VALIDATE_BEARER = "urn:innovation-district.com:oauth2:grant_type:validate_bearer";
  private static final String TOKEN = "token";

  private final Logger logger = LoggerFactory.getLogger(TokenValidationService.class);

  @Resource
  private ObjectMapper objectMapper;
  @Resource
  private RestTemplate restTemplate;
  @Resource
  private TokenServerConfig tokenServerConfig;

  public Optional<TokenValidationResult> validateAccessToken(final String accessToken) {

    final HttpEntity<?> entity = createRequestEntity(accessToken);
    final String url = tokenServerConfig.getBaseUri() + "/token";

    final ResponseEntity<String> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    } catch (final RestClientException exception) {
      logger.warn("Failed to validate access token: {}.", exception.getMessage());
      return Optional.empty();
    }

    final Map<String, String> jsonMap;
    try {
      jsonMap = objectMapper.readValue(response.getBody(), Map.class);
    } catch (final IOException e) {
      logger.error("Failed to parse json from access token validation response '{}' with exception message: {}", response.getBody(), e.getMessage());
      return Optional.empty();
    }

    final TokenValidationResult tokenValidationResult = createTokenValidationResult(jsonMap);
    return Optional.of(tokenValidationResult);
  }

  private HttpEntity<?> createRequestEntity(final String accessToken) {
    final HttpHeaders headers = createTokenValidationRequestHeaders();

    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add(GRANT_TYPE, VALIDATE_BEARER);
    formData.add(TOKEN, accessToken);
    return new HttpEntity<Object>(formData, headers);
  }

  private HttpHeaders createTokenValidationRequestHeaders() {
    final HttpHeaders headers = new HttpHeaders();
    final String authorizationHeaderValue = new BasicAuthenticationHeaderBuilder()
        .withUsername(tokenServerConfig.getClientId())
        .withPassword(tokenServerConfig.getClientSecret())
        .build();

    headers.add(AUTHORIZATION, authorizationHeaderValue);
    headers.add(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    return headers;
  }

  private TokenValidationResult createTokenValidationResult(final Map<String, String> jsonMap) {
    final String userId = jsonMap.get("reference_id");
    final String scopes = jsonMap.get("scope");

    return new TokenValidationResult(userId, scopes);
  }

}
