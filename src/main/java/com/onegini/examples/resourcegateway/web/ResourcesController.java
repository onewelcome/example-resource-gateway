package com.onegini.examples.resourcegateway.web;

import static com.onegini.examples.resourcegateway.ResourceGatewayConstants.BEARER_PREFIX;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildBadRequestResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidAccessTokenResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidScopeResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.TokenValidationResult;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.ScopeValidationService;
import com.onegini.examples.resourcegateway.service.TokenValidationService;

@RestController
@RequestMapping(value = "/resources")
public class ResourcesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesController.class);

  @Resource
  private TokenValidationService tokenValidationService;
  @Resource
  private ScopeValidationService scopeValidationService;
  @Resource
  private DeviceApiRequestService deviceApiRequestService;
  @Resource
  private AccessTokenExtractor accessTokenExtractor;

  @RequestMapping(value = "/devices", method = RequestMethod.GET)
  public ResponseEntity<?> getDevices(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final Optional<String> accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final boolean noAccessTokenFound = !accessToken.isPresent();
    if (noAccessTokenFound) {
      LOGGER.warn("Provided authorization header not properly formatted.");
      return buildBadRequestResponse();
    }

    final Optional<TokenValidationResult> optionalTokenValidationResult = tokenValidationService.validateAccessToken(accessToken.get());
    final boolean invalidAccessToken = !optionalTokenValidationResult.isPresent();
    if (invalidAccessToken) {
      return buildInvalidAccessTokenResponse();
    }

    final TokenValidationResult tokenValidationResult = optionalTokenValidationResult.get();

    final boolean readScopeNotGranted = !scopeValidationService.isReadScopeGranted(tokenValidationResult.getScopes());
    if (readScopeNotGranted) {
      return buildInvalidScopeResponse();
    }

    return deviceApiRequestService.getDevices(tokenValidationResult.getUserId());
  }

}
