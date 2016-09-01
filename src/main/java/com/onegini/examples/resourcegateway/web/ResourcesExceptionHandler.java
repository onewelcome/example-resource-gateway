package com.onegini.examples.resourcegateway.web;

import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildBadRequestResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidAccessTokenResponse;
import static com.onegini.examples.resourcegateway.util.ErrorResponseBuilder.buildInvalidScopeResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.onegini.examples.resourcegateway.model.exception.InvalidAccessTokenException;
import com.onegini.examples.resourcegateway.model.exception.NoAccessTokenProvidedException;
import com.onegini.examples.resourcegateway.model.exception.ScopeNotGrantedException;
import com.onegini.examples.resourcegateway.model.exception.TokenServerException;

@ControllerAdvice(annotations = RestController.class)
public class ResourcesExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesExceptionHandler.class);

  @ExceptionHandler(NoAccessTokenProvidedException.class)
  private ResponseEntity<?> handleBadRequestException(final NoAccessTokenProvidedException exception) {
    LOGGER.warn(exception.getMessage());
    return buildBadRequestResponse();
  }

  @ExceptionHandler(InvalidAccessTokenException.class)
  private ResponseEntity<?> handleInvalidAccessTokenException(final InvalidAccessTokenException exception) {
    LOGGER.info(exception.getMessage());
    return buildInvalidAccessTokenResponse();
  }

  @ExceptionHandler(ScopeNotGrantedException.class)
  public ResponseEntity<?> handleScopeNotGrantedException(final ScopeNotGrantedException exception) {
    LOGGER.info(exception.getMessage());
    return buildInvalidScopeResponse();
  }

  @ExceptionHandler(TokenServerException.class)
  public ResponseEntity<?> handleTokenServerException(final TokenServerException exception) {
    LOGGER.warn(exception.getMessage());
    return buildInvalidAccessTokenResponse();
  }
}
