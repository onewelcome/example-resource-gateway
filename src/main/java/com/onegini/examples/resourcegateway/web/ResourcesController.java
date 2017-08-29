package com.onegini.examples.resourcegateway.web;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ApplicationDetails;
import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
import com.onegini.examples.resourcegateway.service.ScopeValidationService;
import com.onegini.examples.resourcegateway.service.tokenintrospection.TokenIntrospectionService;

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

  @RequestMapping(value = "/devices", method = RequestMethod.GET)
  public ResponseEntity<?> getDevices(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionService.introspectAccessToken(accessToken);

    scopeValidationService.validateReadScopeGranted(tokenIntrospectionResult.getScopes());

    return deviceApiRequestService.getDevices(tokenIntrospectionResult.getUserId());
  }

  @RequestMapping(value = "/application-details", method = RequestMethod.GET)
  public ResponseEntity<?> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenIntrospectionResult tokenIntrospectionResult = tokenIntrospectionService.introspectAccessToken(accessToken);

    scopeValidationService.validateApplicationDetailsScopeGranted(tokenIntrospectionResult.getScopes());

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenIntrospectionResult);
    return new ResponseEntity<Object>(applicationDetails, OK);
  }

}
