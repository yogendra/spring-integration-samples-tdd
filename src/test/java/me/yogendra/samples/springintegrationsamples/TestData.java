package me.yogendra.samples.springintegrationsamples;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

class TestData {

  private static final String PAYLOAD = "Message Payload";
  private static final String HEADER_KEY = "key";
  private static final String HEADER_VALUE = "value";

  private static final String UPDATED_PAYLOAD = "Updated Message Payload";
  private static final String UPDATED_HEADER_VALUE = "updated value";
  private static final String UPDATED_HEADER_KEY = "update key";


  static Message<String> testMessage() {
    return message(PAYLOAD, HEADER_KEY, HEADER_VALUE);
  }


  static void verifyTestMessage(Message<?> message) {
    verifyMessage(message, PAYLOAD, HEADER_KEY, HEADER_VALUE);
  }


  static Message<String> updatedTestMessage() {
    return message(UPDATED_PAYLOAD, UPDATED_HEADER_KEY, UPDATED_HEADER_VALUE);
  }

  static void verifyUpdatedTestMessage(Message<?> message) {
    verifyMessage(message, UPDATED_PAYLOAD, UPDATED_HEADER_KEY, UPDATED_HEADER_VALUE);
  }

  private static <T> Message<T> message(T payload, String key, String value) {
    return MessageBuilder.withPayload(payload)
        .setHeader(key, value)
        .build();
  }
  static <T> Message<T> message(T payload){
    return MessageBuilder.withPayload(payload).build();
  }

  private static <T> void verifyMessage(Message<?> message, T payload, String key, String value) {

    verifyMessagePayload(message, payload);
    verifyMessageHeader(message, key, value);
  }

  static <T> void verifyMessagePayload(Message<?> message, T payload) {
    assertThat(message, notNullValue());
    assertThat(message.getPayload(), notNullValue());
    assertThat(message.getPayload(), is(payload));

  }

  private static void verifyMessageHeader(Message<?> message, String key, String value) {

    MessageHeaders headers = message.getHeaders();

    assertThat(headers, notNullValue());
    assertThat(headers.getId(), notNullValue());
    assertThat(headers.getTimestamp(), Matchers.lessThanOrEqualTo(currentTimeMillis()));

    assertThat(headers.get(key), is(value));
  }
}
