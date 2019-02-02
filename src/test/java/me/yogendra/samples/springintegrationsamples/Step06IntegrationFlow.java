package me.yogendra.samples.springintegrationsamples;

import static me.yogendra.samples.springintegrationsamples.TestData.message;
import static me.yogendra.samples.springintegrationsamples.TestData.verifyMessagePayload;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step06IntegrationFlow {

  @Autowired
  @Qualifier("inputChannel")
  private MessageChannel inputChannel;

  @Autowired
  @Qualifier("outputChannel")
  private DirectChannel outputChannel;

  @Test
  public void shouldPostToIntegration() {
    String input = "some text";
    String expected = "SOME TEXT";
    Message<?> message = message(input);

    outputChannel.subscribe(x -> verifyMessagePayload(x, expected));
    inputChannel.send(message);

  }

  @Configuration
  @EnableIntegration
  public static class Context {

    @Bean
    public IntegrationFlow myFlow() {
      return IntegrationFlows.from(inputChannel())
          .transform(String.class, String::toUpperCase)
          .channel(outputChannel())
          .get();
    }

    @Bean
    public DirectChannel outputChannel() {
      return new DirectChannel();
    }

    @Bean
    public MessageChannel inputChannel() {
      return new DirectChannel();
    }

  }


}
