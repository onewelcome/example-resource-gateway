package com.onegini.examples.resourcegateway.service.tokenvalidation;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import javax.annotation.Resource;

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

import com.onegini.examples.resourcegateway.config.TokenServerConfig;
import com.onegini.examples.resourcegateway.model.exception.TokenServerException;
import com.onegini.examples.resourcegateway.util.BasicAuthenticationHeaderBuilder;

@Service
public class TokenValidationRequestExecutor {
  private static final String GRANT_TYPE = "grant_type";
  private static final String VALIDATE_BEARER = "urn:innovation-district.com:oauth2:grant_type:validate_bearer";
  private static final String TOKEN = "token";

  @Resource
  private TokenServerConfig tokenServerConfig;
  @Resource
  private RestTemplate restTemplate;

  public ResponseEntity<String> execute(final String accessToken) {
    final HttpEntity<?> entity = createRequestEntity(accessToken);
    final String url = tokenServerConfig.getBaseUri() + "/token";

    final ResponseEntity<String> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    } catch (final RestClientException exception) {
      throw new TokenServerException(exception);
    }
    return response;
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

}
