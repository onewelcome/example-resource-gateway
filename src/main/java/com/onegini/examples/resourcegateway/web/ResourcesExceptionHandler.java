package com.onegini.examples.resourcegateway.web;

import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidAccessTokenResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidScopeResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildMissingAccessTokenResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.ErrorResponse;
import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import com.onegini.examples.resourcegateway.model.exception.NoAccessTokenProvidedException;
import com.onegini.examples.resourcegateway.model.exception.ScopeNotGrantedException;
import com.onegini.examples.resourcegateway.model.exception.TokenServerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ResourcesExceptionHandler {

  @ExceptionHandler(NoAccessTokenProvidedException.class)
  private ResponseEntity<ErrorResponse> handleNoAccessTokenProvided(final NoAccessTokenProvidedException exception) {
    log.warn(exception.getMessage());
    return buildMissingAccessTokenResponse();
  }

  @ExceptionHandler(InvalidAccessTokenException.class)
  private ResponseEntity<ErrorResponse> handleInvalidAccessTokenException(final InvalidAccessTokenException exception) {
    log.info(exception.getMessage());
    return buildInvalidAccessTokenResponse();
  }

  @ExceptionHandler(ScopeNotGrantedException.class)
  public ResponseEntity<ErrorResponse> handleScopeNotGrantedException(final ScopeNotGrantedException exception) {
    log.info(exception.getMessage());
    return buildInvalidScopeResponse();
  }

  @ExceptionHandler(TokenServerException.class)
  public ResponseEntity<ErrorResponse> handleTokenServerException(final TokenServerException exception) {
    log.warn(exception.getMessage());
    return buildInvalidAccessTokenResponse();
  }
}
