package com.onegini.examples.resourcegateway.web;

import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildBadRequestResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidAccessTokenResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidScopeResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ApplicationDetails;
import com.onegini.examples.resourcegateway.model.TokenValidationResult;
import com.onegini.examples.resourcegateway.model.exception.BadRequestException;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
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

    final TokenValidationResult tokenValidationResult = validateAccessToken(authorizationHeader);

    final boolean readScopeNotGranted = !scopeValidationService.isReadScopeGranted(tokenValidationResult.getScopes());
    if (readScopeNotGranted) {
      return buildInvalidScopeResponse();
    }

    return deviceApiRequestService.getDevices(tokenValidationResult.getUserId());
  }

  @RequestMapping(value = "/application-details", method = RequestMethod.GET)
  public ResponseEntity<?> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {

    final TokenValidationResult tokenValidationResult = validateAccessToken(authorizationHeader);

    final boolean applicationDetailsScopeNotGranted = !scopeValidationService.isApplicationDetailsScopeGranted(tokenValidationResult.getScopes());
    if (applicationDetailsScopeNotGranted) {
      return buildInvalidScopeResponse();
    }

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenValidationResult);
    return new ResponseEntity<Object>(applicationDetails, OK);
  }

  private TokenValidationResult validateAccessToken(final String authorizationHeader) {
    final Optional<String> accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    final boolean noAccessTokenFound = !accessToken.isPresent();
    if (noAccessTokenFound) {
      throw new BadRequestException("Provided authorization header not properly formatted.");
    }

    final Optional<TokenValidationResult> optionalTokenValidationResult = tokenValidationService.validateAccessToken(accessToken.get());
    final boolean invalidAccessToken = !optionalTokenValidationResult.isPresent();
    if (invalidAccessToken) {
      throw new InvalidAccessTokenException();
    }

    return optionalTokenValidationResult.get();
  }

  @ExceptionHandler(BadRequestException.class)
  private ResponseEntity<?> handleBadRequestException(BadRequestException exception) {
    LOGGER.warn(exception.getMessage());
    return buildBadRequestResponse();
  }

  @ExceptionHandler(InvalidAccessTokenException.class)
  private ResponseEntity<?> handleInvalidAccessTokenException() {
    return buildInvalidAccessTokenResponse();
  }



}
