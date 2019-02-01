package me.yogendra.samples.springintegrationsamples;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public class MessagePrintServiceTest {

  private MessagePrintService printer;
  private ByteArrayOutputStream bytes;

  @Before
  public void setUp(){
    bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    printer = new MessagePrintService(out);
  }
  @Test
  public void shouldPrintMessage() {

    Map<String, Object> headers = new HashMap<>();
    headers.put("headerKey", "headerValue");
    Message<String> input = new GenericMessage<>("Hello, World!", headers);

    printer.print(input);

    String output = getOutput();


    assertThat(output, containsString("headerKey=headerValue"));
    assertThat(output, containsString("Hello, World!"));

  }

  private String getOutput() {
    return new String(bytes.toByteArray());
  }

}