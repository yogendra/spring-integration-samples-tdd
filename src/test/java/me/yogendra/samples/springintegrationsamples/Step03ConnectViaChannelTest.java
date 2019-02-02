package me.yogendra.samples.springintegrationsamples;

import static me.yogendra.samples.springintegrationsamples.TestData.testMessage;
import static me.yogendra.samples.springintegrationsamples.TestData.verifyTestMessage;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step03ConnectViaChannelTest {

  @Autowired
  private DirectChannel inputChannel;

  @Test
  public void shouldCreateAnInputChannel() {
    assertThat(inputChannel, notNullValue());
  }

  @Test
  public void shouldBeAbleToSubscribeChannel() {
    inputChannel.subscribe(this::verifyMessage);

    Message<String> message = testMessage();

    inputChannel.send(message);

  }

  public void verifyMessage(Message<?> message) {
    verifyTestMessage(message);
  }

  @Configuration
  public static class IntegrationConfiguration {

    @Bean
    public MessageChannel inputChannel() {
      return new DirectChannel();
    }
  }

}
