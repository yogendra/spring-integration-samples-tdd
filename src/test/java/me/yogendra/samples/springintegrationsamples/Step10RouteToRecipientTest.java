package me.yogendra.samples.springintegrationsamples;

import static java.util.Arrays.asList;
import static java.util.stream.Stream.of;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class Step10RouteToRecipientTest {


  @Autowired
  private QueueChannel multipleOfThreeChannel;


  @Autowired
  private QueueChannel remainderIsOneChannel;

  @Autowired
  private QueueChannel remainderIsTwoChannel;

  @Autowired
  private NumbersClassifier numbersClassifier;

  @Test
  public void shouldClassifyNumbers() {

    numbersClassifier.classify(asList(1, 2, 3, 4, 5, 6, 7, 8));

    verifyChannel(multipleOfThreeChannel, 3, 6);
    verifyChannel(remainderIsOneChannel, 1, 4, 7);
    verifyChannel(remainderIsTwoChannel, 2, 5, 8);


  }

  private void verifyChannel(QueueChannel channel, Integer... numbers) {

    assertThat(channel.getQueueSize(), is(numbers.length));
    of(numbers)
        .forEach(number -> {
          Message<?> message = channel.receive(0);
          assertThat(message, notNullValue());
          assertThat(message.getPayload(), is(number));
        });
    Message<?> message = channel.receive(0);
    assertThat(message, nullValue());
  }


  @MessagingGateway
  interface NumbersClassifier {

    @Gateway(requestChannel = "classify.input")
    public void classify(Collection<Integer> numbers);

  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  public static class IntegrationConfiguration {

    @Bean
    public IntegrationFlow classify() {
      return flow -> flow
          .split()
          .routeToRecipients(router -> router
              .recipient("multipleOfThreeChannel", this::isMultipleOfThree)
              .recipient("remainderIsOneChannel", this::hasRemainderOne)
              .recipient("remainderIsTwoChannel", this::hasRemainderTwo)
          );
    }


    @Bean
    QueueChannel multipleOfThreeChannel() {
      return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIsOneChannel() {
      return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIsTwoChannel() {
      return new QueueChannel();
    }

    private boolean isMultipleOfThree(Integer integer) {
      return integer % 3 == 0;
    }

    private boolean hasRemainderOne(Integer integer) {
      return integer % 3 == 1;
    }

    private boolean hasRemainderTwo(Integer integer) {
      return integer % 3 == 2;
    }
  }

}
