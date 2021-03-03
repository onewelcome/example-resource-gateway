package com.onegini.examples.resourcegateway.web;

import static com.onegini.examples.resourcegateway.service.ScopeValidationService.SCOPE_APPLICATION_DETAILS;
import static com.onegini.examples.resourcegateway.service.ScopeValidationService.SCOPE_READ;
import static com.onegini.examples.resourcegateway.service.ScopeValidationService.SCOPE_WRITE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ApplicationDetails;
import com.onegini.examples.resourcegateway.model.DecoratedUser;
import com.onegini.examples.resourcegateway.model.DeviceList;
import com.onegini.examples.resourcegateway.model.FormDataWithFiles;
import com.onegini.examples.resourcegateway.model.MultipartResponse;
import com.onegini.examples.resourcegateway.model.TokenIntrospectionResult;
import com.onegini.examples.resourcegateway.service.AccessTokenExtractor;
import com.onegini.examples.resourcegateway.service.DeviceApiRequestService;
import com.onegini.examples.resourcegateway.service.MultipartService;
import com.onegini.examples.resourcegateway.service.ScopeValidationService;
import com.onegini.examples.resourcegateway.service.TokenTypeValidationService;
import com.onegini.examples.resourcegateway.service.tokenintrospection.TokenIntrospectionService;
import com.onegini.examples.resourcegateway.util.DecoratedUserIdBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/resources")
@RequiredArgsConstructor
public class ResourcesController {

  private final TokenIntrospectionService tokenIntrospectionService;
  private final ScopeValidationService scopeValidationService;
  private final DeviceApiRequestService deviceApiRequestService;
  private final MultipartService multipartService;
  private final AccessTokenExtractor accessTokenExtractor;
  private final TokenTypeValidationService tokenTypeValidationService;

  @GetMapping(value = "/devices")
  public ResponseEntity<DeviceList> getDevices(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResultFromHeader(authorizationHeader);
    validateScopeAndTokenType(tokenIntrospectionResult, SCOPE_READ);

    return deviceApiRequestService.getDevices(tokenIntrospectionResult.getSub());
  }

  @GetMapping(value = "/application-details")
  public ResponseEntity<ApplicationDetails> getApplicationDetails(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResultFromHeader(authorizationHeader);
    validateScopeAndTokenType(tokenIntrospectionResult, SCOPE_APPLICATION_DETAILS);

    final ApplicationDetails applicationDetails = new ApplicationDetails(tokenIntrospectionResult.getAppIdentifier(), tokenIntrospectionResult.getAppPlatform(),
        tokenIntrospectionResult.getAppVersion());

    return new ResponseEntity<>(applicationDetails, OK);
  }

  @GetMapping(value = "/user-id-decorated")
  public ResponseEntity<DecoratedUser> getDecoratedUserId(@RequestHeader(AUTHORIZATION) final String authorizationHeader) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResultFromHeader(authorizationHeader);

    tokenTypeValidationService.validateImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());

    final DecoratedUser decoratedUser = new DecoratedUserIdBuilder()
        .withUserId(tokenIntrospectionResult.getSub())
        .build();

    return new ResponseEntity<>(decoratedUser, OK);
  }

  @PostMapping(value = "upload")
  public ResponseEntity<MultipartResponse> upload(@RequestHeader(AUTHORIZATION) final String authorizationHeader,
                                                  @ModelAttribute final FormDataWithFiles formDataWithFiles) {
    final TokenIntrospectionResult tokenIntrospectionResult = getTokenIntrospectionResultFromHeader(authorizationHeader);
    validateScopeAndTokenType(tokenIntrospectionResult, SCOPE_WRITE);

    final MultipartResponse multipartResponse = multipartService.parse(formDataWithFiles);

    return new ResponseEntity<>(multipartResponse, OK);
  }

  private void validateScopeAndTokenType(final TokenIntrospectionResult tokenIntrospectionResult, final String requiredScope) {
    scopeValidationService.validateScopeGranted(tokenIntrospectionResult.getScope(), requiredScope);
    tokenTypeValidationService.validateNoImplicitAuthenticationToken(tokenIntrospectionResult.getAmr());
  }

  private TokenIntrospectionResult getTokenIntrospectionResultFromHeader(final String authorizationHeader) {
    final String accessToken = accessTokenExtractor.extractFromHeader(authorizationHeader);
    return tokenIntrospectionService.introspectAccessToken(accessToken);
  }
}
