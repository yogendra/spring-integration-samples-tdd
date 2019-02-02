package me.yogendra.samples.springintegrationsamples;

import static me.yogendra.samples.springintegrationsamples.TestData.testMessage;
import static me.yogendra.samples.springintegrationsamples.TestData.updatedTestMessage;
import static me.yogendra.samples.springintegrationsamples.TestData.verifyTestMessage;
import static me.yogendra.samples.springintegrationsamples.TestData.verifyUpdatedTestMessage;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class Step05ServiceActivatorWithResponseTest {


  @Autowired
  private MyService myService;

  @Autowired
  @Qualifier("inputChannel")
  private MessageChannel inputChannel;

  @Autowired
  @Qualifier("outputChannel")
  private DirectChannel outputChannel;


  @Test
  public void shouldAutowireBeans() {
    assertThat(myService, notNullValue());
    assertThat(inputChannel, notNullValue());
  }

  @Test
  public void shouldInvokeServiceMethod() {
    Message<String> message = testMessage();
    outputChannel.subscribe(this::verify);
    inputChannel.send(message);

  }

  private void verify(Message<?> message) {
    verifyUpdatedTestMessage(message);
  }


  @Slf4j
  public static class MyService {

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public Message<String> doSomething(Message<String> message) {
      log.info("Received message");
      verifyTestMessage(message);
      return updatedTestMessage();


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
    public DirectChannel outputChannel() {
      return new DirectChannel();
    }

    @Bean
    public MyService myService() {
      return new MyService();
    }

  }
}
