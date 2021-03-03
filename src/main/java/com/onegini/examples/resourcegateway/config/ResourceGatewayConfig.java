package com.onegini.examples.resourcegateway.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class ResourceGatewayConfig {

  @Bean
  public Jackson2ObjectMapperBuilder jacksonBuilder() {
    final Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
    b.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .serializationInclusion(JsonInclude.Include.NON_NULL);
    return b;
  }

  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    return builder
        .build();
  }

}
