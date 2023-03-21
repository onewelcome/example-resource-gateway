package com.onegini.examples.resourcegateway.service.tokenintrospection;

import com.onegini.examples.resourcegateway.config.TokenServerConfig;
import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;
import com.onegini.examples.resourcegateway.model.exception.TokenServerException;
import com.onegini.examples.resourcegateway.util.BasicAuthenticationHeaderBuilder;
import jakarta.annotation.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
public class TokenIntrospectionRequestExecutor {
  private static final String TOKEN = "token";
  private static final String ENDPOINT_INTROSPECT = "/api/v1/token/introspect";

  @Resource
  private TokenServerConfig tokenServerConfig;
  @Resource
  private RestTemplate restTemplate;

  public ResponseEntity<TokenIntrospectionResult> execute(final String accessToken) {
    final HttpEntity<?> entity = createRequestEntity(accessToken);
    final String url = tokenServerConfig.getBaseUri() + ENDPOINT_INTROSPECT;
    final ResponseEntity<TokenIntrospectionResult> response;

    try {
      response = restTemplate.exchange(url, HttpMethod.POST, entity, TokenIntrospectionResult.class);
    } catch (final RestClientException exception) {
      throw new TokenServerException(exception);
    }

    return response;
  }

  private HttpEntity<?> createRequestEntity(final String accessToken) {
    final HttpHeaders headers = createTokenIntrospectionRequestHeaders();
    final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

    formData.add(TOKEN, accessToken);

    return new HttpEntity<Object>(formData, headers);
  }

  private HttpHeaders createTokenIntrospectionRequestHeaders() {
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
