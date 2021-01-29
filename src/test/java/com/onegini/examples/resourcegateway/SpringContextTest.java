package com.onegini.examples.resourcegateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.onegini.examples.resourcegateway.web.ResourcesController;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest(classes = { ResourceGatewayApplication.class })
class SpringContextTest {

  @Resource
  private ResourcesController resourcesController;

  @Test
  void should_injected() {
    assertThat(resourcesController).isNotNull();
  }

}