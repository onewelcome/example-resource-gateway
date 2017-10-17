package com.onegini.examples.resourcegateway.web;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ApplicationDetails;
import com.onegini.examples.resourcegateway.model.DecoratedUserId;
import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
import com.onegini.examples.resourcegateway.service.ScopeValidationService;
import com.onegini.examples.resourcegateway.service.TokenTypeValidationService;
import com.onegini.examples.resourcegateway.service.tokenintrospection.TokenIntrospectionService;
import com.onegini.examples.resourcegateway.util.DecoratedUserIdBuilder;

@RestController
@RequestMapping(value = "/resources")
public class ResourcesController {

  @Resource
  private TokenIntrospectionService tokenIntrospectionService;
  @Resource
  private ScopeValidationService scopeValidationService;
  @Resource
  private DeviceApiRequestService deviceApiRequestService;
  @Resource
  private AccessTokenExtractor accessTokenExtractor;
  @Resource
  private TokenTypeValidationService tokenTypeValidationService;

  @RequestMapping(value = "/devices", method = RequestMethod.GET)
  public ResponseEntity<?> getDevices(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResult(authorizationHeader);

    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());
    scopeValidationService.validateReadScopeGranted(tokenIntrospectionResult.getScope());

    return deviceApiRequestService.getDevices(tokenIntrospectionResult.getSub());
  }

  @RequestMapping(value = "/application-details", method = RequestMethod.GET)
  public ResponseEntity<?> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResult(authorizationHeader);

    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());
    scopeValidationService.validateApplicationDetailsScopeGranted(tokenIntrospectionResult.getScope());

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenIntrospectionResult.getAppIdentifier(), tokenIntrospectionResult.getAppPlatform(),
        tokenIntrospectionResult.getAppVersion());

    return new ResponseEntity<Object>(applicationDetails, OK);
  }

  @RequestMapping(value = "/user-id-decorated", method = RequestMethod.GET)
  public ResponseEntity<?> getDecoratedUserId(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResult(authorizationHeader);

    tokenTypeValidationService.validateImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());

    final DecoratedUserId decoratedUserId = new DecoratedUserIdBuilder()
        .withUserId(tokenIntrospectionResult.getSub())
        .build();

    return new ResponseEntity<>(decoratedUserId, OK);
  }

  @RequestMapping(value = "/mirror-request", method = RequestMethod.POST)
  public ResponseEntity<byte[]> mirrorRequest(@RequestHeader(AUTHORIZATION) final String authorizationHeader,
                                              @RequestHeader(value = CONTENT_TYPE, required = false) final String contentType,
                                              @RequestBody(required = false) final byte[] requestBody) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResult(authorizationHeader);

    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());
    scopeValidationService.validateReadScopeGranted(tokenIntrospectionResult.getScope());

    LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.set(CONTENT_TYPE, contentType);

    return new ResponseEntity<>(requestBody, headers, OK);
  }

  private TokenIntrospectionResult getTokenIntrospectionResult(final @RequestHeader(AUTHORIZATION) String authorizationHeader) {
    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    return tokenIntrospectionService.introspectAccessToken(accessToken);
  }
}
