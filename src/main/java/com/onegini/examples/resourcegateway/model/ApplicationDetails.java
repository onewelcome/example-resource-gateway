package com.onegini.examples.resourcegateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetails {

  private String applicationIdentifier;
  private String applicationPlatform;
  private String applicationVersion;

}
