package me.yogendra.samples.springintegrationsamples;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;


public class Step01BasicTests {
  private static final String PAYLOAD = "Hello, World!";
  @Test
  public void shouldBeAbleToCreateMessageWithPayload(){


    Message<String> message = new GenericMessage<>(PAYLOAD);

    String output = message.getPayload();

    assertThat(output, is(PAYLOAD));
  }

  @Test
  public void shouldBeAbleToAddHeadersToMessage(){
    String headerKey = "key";
    String headerValue = "value";

    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put(headerKey, headerValue);

    MessageHeaders headers = new MessageHeaders(headerMap);

    Message<String> message = new GenericMessage<>(PAYLOAD, headers);

    assertThat(message.getPayload(), is(PAYLOAD));
    assertThat(message.getHeaders(), notNullValue());

    assertThat(message.getHeaders().get(headerKey), notNullValue());
    assertThat(message.getHeaders().get(headerKey), is(headerValue));
  }
}
