package me.yogendra.samples.springintegrationsamples;

import static me.yogendra.samples.springintegrationsamples.TestData.testMessage;
import static me.yogendra.samples.springintegrationsamples.TestData.verifyTestMessage;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step04ServiceActivatorTest {


  @Autowired
  private MyService myService;

  @Autowired
  private MessageChannel inputChannel;

  @Test
  public void shouldAutowireBeans() {
    assertThat(myService, notNullValue());
    assertThat(inputChannel, notNullValue());
  }

  @Test
  public void shouldInvokeServiceMethod() {
    Message<String> message = testMessage();
    inputChannel.send(message);
  }


  @Slf4j
  public static class MyService {

    @ServiceActivator(inputChannel = "inputChannel")
    public void doSomething(Message<String> message) {
      log.info("Received message");
      verifyTestMessage(message);
    }

  }


  @Configuration
  @EnableIntegration
  public static class IntegrationConfiguration {

    @Bean
    public MessageChannel inputChannel() {
      return new DirectChannel();
    }

    @Bean
    public MyService myService() {
      return new MyService();
    }

  }
}
