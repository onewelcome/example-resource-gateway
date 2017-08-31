package com.onegini.examples.resourcegateway.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onegini.examples.resourcegateway.config.DeviceApiConfig;
import com.onegini.examples.resourcegateway.model.Devices;
import com.onegini.examples.resourcegateway.util.BasicAuthenticationHeaderBuilder;

@Service
public class DeviceApiRequestService {

  private static final String DEVICE_API_PATH = "/api/v2/users/{user_id}/devices";

  @Resource
  private RestTemplate restTemplate;
  @Resource
  private DeviceApiConfig deviceApiConfig;

  public ResponseEntity<Devices> getDevices(final String userId) {
    final HttpEntity<?> requestEntity = createRequestEntity();
    final String uri = deviceApiConfig.getServerRoot() + DEVICE_API_PATH;

    final ResponseEntity<Devices> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Devices.class, userId);
    final Devices devices = response.getBody();
    return new ResponseEntity<>(devices, OK);
  }

  private HttpEntity<?> createRequestEntity() {
    final HttpHeaders headers = new HttpHeaders();
    final String authorizationHeaderValue = new BasicAuthenticationHeaderBuilder()
        .withUsername(deviceApiConfig.getUsername())
        .withPassword(deviceApiConfig.getPassword())
        .build();
    headers.add(AUTHORIZATION, authorizationHeaderValue);

    return new HttpEntity<>(headers);
  }
}
