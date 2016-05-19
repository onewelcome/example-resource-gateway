package com.onegini.examples.resourcegateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@SpringBootApplication
public class ResourceGatewayApplication {

  private static final int TIMEOUT_IN_MILLIS = 3000;

  public static void main(final String[] args) {
    SpringApplication.run(ResourceGatewayApplication.class, args);
  }

  @Autowired
  public void configureJackson(final ObjectMapper jackson2ObjectMapper) {
    jackson2ObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    jackson2ObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpComponentsClientHttpRequestFactory.setReadTimeout(TIMEOUT_IN_MILLIS);
    httpComponentsClientHttpRequestFactory.setConnectTimeout(TIMEOUT_IN_MILLIS);

    return new RestTemplate(httpComponentsClientHttpRequestFactory);
  }

}
