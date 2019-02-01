package me.yogendra.samples.springintegrationsamples;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class Step02MessageBuilderTest {

  @Test
  public void shouldCreateAMessageWithPayloadAndHeaders(){
    Message<String> message = MessageBuilder.withPayload("Hello, World!")
        .setHeader("key", "value")
        .build();

    assertThat(message.getPayload(), is("Hello, World!"));
    assertThat(message.getHeaders().getId(), notNullValue());
    assertThat(message.getHeaders().get("key"), is("value"));
  }

}
