package me.yogendra.samples.springintegrationsamples;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step07GatewayTest {

  @Autowired
  private GreetingGateway greetingGateway;

  @Test
  public void shouldSendMessageViaGateway() {

    String input = "This is a message";
    String expected = "THIS IS A MESSAGE";
    String output = greetingGateway.greet(input);
    assertThat(output, is(expected));


  }

  @MessagingGateway
  interface GreetingGateway {

    @Gateway(requestChannel = "greeting.input")
    String greet(String subject);
  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  public static class TestConfiguration {

    @Bean
    public IntegrationFlow greeting() {
      return f -> f.<String, String>transform(String::toUpperCase);

    }

  }

}
