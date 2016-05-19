package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.ResourceGatewayConstants.BEARER_PREFIX;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenExtractor {

  public Optional<String> extractFromHeader(final String authorizationHeaderValue) {
    if (isInvalidAuthorizationHeaderFormat(authorizationHeaderValue)) {
      return Optional.empty();
    }

    final String accessToken = StringUtils.removeStart(authorizationHeaderValue, BEARER_PREFIX);
    return Optional.of(accessToken);
  }

  private boolean isInvalidAuthorizationHeaderFormat(final String authorizationHeader) {
    return StringUtils.isBlank(authorizationHeader)
        || !StringUtils.startsWithIgnoreCase(authorizationHeader, BEARER_PREFIX)
        || authorizationHeader.length() == BEARER_PREFIX.length();
  }

}
