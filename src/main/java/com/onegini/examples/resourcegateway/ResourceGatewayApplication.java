package com.onegini.examples.resourcegateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
  private ObjectMapper objectMapper;

  @Autowired
  public void configureJackson(final ObjectMapper jackson2ObjectMapper) {
    jackson2ObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    jackson2ObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);
    return converter;
  }

  @Bean
  public RestTemplate getRestTemplate() {
    final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpComponentsClientHttpRequestFactory.setReadTimeout(TIMEOUT_IN_MILLIS);
    httpComponentsClientHttpRequestFactory.setConnectTimeout(TIMEOUT_IN_MILLIS);

    final RestTemplate template = new RestTemplate(httpComponentsClientHttpRequestFactory);
    template.getMessageConverters().add(0, mappingJackson2HttpMessageConverter());

    return template;
  }

}
