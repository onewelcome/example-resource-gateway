package com.onegini.examples.resourcegateway.web;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ApplicationDetails;
import com.onegini.examples.resourcegateway.model.DecoratedUser;
import com.onegini.examples.resourcegateway.model.DeviceList;
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

  @GetMapping(value = "/devices")
  public ResponseEntity<DeviceList> getDevices(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionService.introspectAccessToken(accessToken);

    scopeValidationService.validateReadScopeGranted(tokenIntrospectionResult.getScope());
    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());

    return deviceApiRequestService.getDevices(tokenIntrospectionResult.getSub());
  }

  @GetMapping(value = "/application-details")
  public ResponseEntity<ApplicationDetails> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionService.introspectAccessToken(accessToken);

    scopeValidationService.validateApplicationDetailsScopeGranted(tokenIntrospectionResult.getScope());
    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenIntrospectionResult.getAppIdentifier(), tokenIntrospectionResult.getAppPlatform(),
        tokenIntrospectionResult.getAppVersion());

    return new ResponseEntity<>(applicationDetails, OK);
  }

  @GetMapping(value = "/user-id-decorated")
  public ResponseEntity<DecoratedUser> getDecoratedUserId(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionService.introspectAccessToken(accessToken);

    tokenTypeValidationService.validateImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());

    final DecoratedUser decoratedUser = new DecoratedUserIdBuilder()
        .withUserId(tokenIntrospectionResult.getSub())
        .build();

    return new ResponseEntity<>(decoratedUser, OK);
  }
}
