package com.onegini.examples.resourcegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@SpringBootApplication
public class ResourceGatewayApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ResourceGatewayApplication.class, args);
  }

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
