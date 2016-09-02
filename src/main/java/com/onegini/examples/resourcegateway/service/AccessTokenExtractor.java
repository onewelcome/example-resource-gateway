package com.onegini.examples.resourcegateway.service;

import static com.onegini.examples.resourcegateway.ResourceGatewayConstants.BEARER_PREFIX;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onegini.examples.resourcegateway.model.exception.NoAccessTokenProvidedException;

@Service
public class AccessTokenExtractor {

  public String extractFromHeader(final String authorizationHeaderValue) {
    if (isInvalidAuthorizationHeaderFormat(authorizationHeaderValue)) {
      final String message = String.format("Authorization header value `%s` does not contain an access token.", authorizationHeaderValue);
      throw new NoAccessTokenProvidedException(message);
    }

    return StringUtils.removeStart(authorizationHeaderValue, BEARER_PREFIX);
  }

  private boolean isInvalidAuthorizationHeaderFormat(final String authorizationHeader) {
    return StringUtils.isBlank(authorizationHeader)
        || !StringUtils.startsWithIgnoreCase(authorizationHeader, BEARER_PREFIX)
        || authorizationHeader.length() == BEARER_PREFIX.length();
  }

}
