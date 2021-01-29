package com.onegini.examples.resourcegateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.onegini.examples.resourcegateway.web.ResourcesController;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest(classes = { ResourceGatewayApplication.class })
public class SpringContextTest {

  @Resource
  private ResourcesController resourcesController;

  @Test
  public void should_injected() {
    assertThat(resourcesController).isNotNull();
  }

}