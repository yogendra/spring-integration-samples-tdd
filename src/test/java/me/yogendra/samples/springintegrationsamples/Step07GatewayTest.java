package me.yogendra.samples.springintegrationsamples;

import static me.yogendra.samples.springintegrationsamples.TestData.testMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step07GatewayTest {

  @Autowired
  GreetingGateway greetingGateway;

  @Test
  public void shouldSendMessageViaGateway(){
    Message<String> message = testMessage();
  }

  @Component
  @MessagingGateway
  public interface GreetingGateway{
    @Gateway
    String greet(String subject);
  }

  @Configuration
  @EnableIntegration
  @ComponentScan
  public static class TestConfiguration{
  }

}
