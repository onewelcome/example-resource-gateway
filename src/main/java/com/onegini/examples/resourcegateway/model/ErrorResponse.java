package com.onegini.examples.resourcegateway.model;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse implements Serializable {

  private final String error;
  private final String errorDescription;

}
