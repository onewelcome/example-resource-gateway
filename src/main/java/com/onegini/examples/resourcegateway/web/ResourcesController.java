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
import com.onegini.examples.resourcegateway.model.TokenValidationResult;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
import com.onegini.examples.resourcegateway.service.ScopeValidationService;
import com.onegini.examples.resourcegateway.service.tokenvalidation.TokenValidationService;

@RestController
@RequestMapping(value = "/resources")
public class ResourcesController {

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

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenValidationResult tokenValidationResult = tokenValidationService.validateAccessToken(accessToken);

    scopeValidationService.validateReadScopeGranted(tokenValidationResult.getScopes());

    return deviceApiRequestService.getDevices(tokenValidationResult.getUserId());
  }

  @RequestMapping(value = "/application-details", method = RequestMethod.GET)
  public ResponseEntity<?> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final TokenValidationResult tokenValidationResult = tokenValidationService.validateAccessToken(accessToken);

    scopeValidationService.validateApplicationDetailsScopeGranted(tokenValidationResult.getScopes());

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenValidationResult);
    return new ResponseEntity<Object>(applicationDetails, OK);
  }

}
