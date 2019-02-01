package me.yogendra.samples.springintegrationsamples;

import java.io.PrintStream;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MessagePrintService {

  private PrintStream out;
  private static final Logger logger = LoggerFactory.getLogger(MessagePrintService.class);

  public MessagePrintService() {
    this(null);
  }

  public MessagePrintService(@Autowired(required = false) PrintStream out) {
    this.out = out;
  }

  void print(Message<?> message) {
    printHeaders(message);
    printPayload(message);

  }

  private void printPayload(Message<?> message) {
    doPrint(message.getPayload().toString());
  }

  private void printHeaders(Message<?> message) {
    message.getHeaders().entrySet()
        .forEach(this::printEntry);

  }

  private void printEntry(Entry<String, Object> x) {
    doPrint(String.format("%s=%s", x.getKey(), x.getValue().toString()));
  }

  private void doPrint(String string) {
    if (out == null) {
      logger.info(string);
    } else {
      out.print(string);
      out.flush();
    }
  }
}
