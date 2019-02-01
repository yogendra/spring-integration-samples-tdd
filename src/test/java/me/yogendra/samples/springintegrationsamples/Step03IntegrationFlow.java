package me.yogendra.samples.springintegrationsamples;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step03IntegrationFlow {

  @Autowired
  private ByteArrayOutputStream bytes;

  @Autowired
  private MessageChannel inputChannel;


  @Configuration
  @EnableIntegration
  public static class Context {

    @Bean
    public IntegrationFlow myFlow() {
      return IntegrationFlows
          .from(inputChannel())
          .log()
          .transform(String.class, String::toUpperCase)
          .log()
          .handle(printer()::print)
          .get();
    }

    @Bean
    public MessageChannel inputChannel() {
      return new DirectChannel();
    }

    @Bean
    public MessagePrintService printer() {
      return new MessagePrintService(new PrintStream(outputStream()));
    }

    @Bean
    public ByteArrayOutputStream outputStream() {
      return new ByteArrayOutputStream();
    }

  }

  @Test
  public void shouldPostToIntegration() {

    Message<?> message = MessageBuilder.withPayload("Hello, World!")
        .setHeader("key", "value")
        .build();

    inputChannel.send(message);

    String output = new String(bytes.toByteArray());
    assertThat(output, containsString("HELLO, WORLD!"));


  }

}
