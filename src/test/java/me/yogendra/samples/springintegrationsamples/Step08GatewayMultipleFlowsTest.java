package me.yogendra.samples.springintegrationsamples;

import static java.util.Arrays.asList;
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
public class Step08GatewayMultipleFlowsTest {


  @Autowired
  private QueueChannel multipleOfThreeChannel;


  @Autowired
  private QueueChannel remainderIsOneChannel;

  @Autowired
  private QueueChannel remainderIsTwoChannel;

  @Autowired
  private NumbersClassifier numbersClassifier;

  @Test
  public void shouldGetMultiplesOfThree() {

    numbersClassifier.multipleOfThree(asList(1, 2, 3, 4, 5, 6, 7, 8));
    Message<?> number = multipleOfThreeChannel.receive(0);
    assertThat(number.getPayload(), is(3));
    number = multipleOfThreeChannel.receive(0);
    assertThat(number.getPayload(), is(6));
    number = multipleOfThreeChannel.receive(0);
    assertThat(number, nullValue());

  }

  @Test
  public void shouldGetNumberWithOneRemainder() {

    numbersClassifier.remainderIsOne(asList(1, 2, 3, 4, 5, 6, 7, 8));
    Message<?> number = remainderIsOneChannel.receive(0);
    assertThat(number.getPayload(), is(1));
    number = remainderIsOneChannel.receive(0);
    assertThat(number.getPayload(), is(4));
    number = remainderIsOneChannel.receive(0);
    assertThat(number.getPayload(), is(7));
    number = remainderIsOneChannel.receive(0);
    assertThat(number, nullValue());

  }

  @Test
  public void shouldGetNumberWithTwoRemainder() {

    numbersClassifier.remainderIsTwo(asList(1, 2, 3, 4, 5, 6, 7, 8));
    Message<?> number = remainderIsTwoChannel.receive(0);
    assertThat(number.getPayload(), is(2));
    number = remainderIsTwoChannel.receive(0);
    assertThat(number.getPayload(), is(5));
    number = remainderIsTwoChannel.receive(0);
    assertThat(number.getPayload(), is(8));
    number = remainderIsTwoChannel.receive(0);
    assertThat(number, nullValue());

  }

  @MessagingGateway
  interface NumbersClassifier {

    @Gateway(requestChannel = "multipleOfThreeFlow.input")
    void multipleOfThree(Collection<Integer> numbers);

    @Gateway(requestChannel = "remainderIsOneFlow.input")
    void remainderIsOne(Collection<Integer> numbers);

    @Gateway(requestChannel = "remainderIsTwoFlow.input")
    void remainderIsTwo(Collection<Integer> numbers);

  }

  @Configuration
  @EnableIntegration
  @IntegrationComponentScan
  public static class IntegrationConfiguration {

    @Bean
    public IntegrationFlow multipleOfThreeFlow() {
      return flow -> flow.split()
          .<Integer>filter(this::isMultipleOfThree)
          .channel("multipleOfThreeChannel");
    }

    @Bean
    public IntegrationFlow remainderIsOneFlow() {
      return flow -> flow.split()
          .<Integer>filter(this::hasRemainderOne)
          .channel("remainderIsOneChannel");
    }

    @Bean
    public IntegrationFlow remainderIsTwoFlow() {
      return flow -> flow.split()
          .<Integer>filter(this::hasRemainderTwo)
          .channel("remainderIsTwoChannel");
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
