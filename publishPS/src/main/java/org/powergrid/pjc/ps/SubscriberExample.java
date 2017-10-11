package org.powergrid.pjc.ps;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.SubscriptionName;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SubscriberExample {

  // use the default project id
  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();

  static class MessageReceiverExample implements MessageReceiver {

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
      messages.offer(message);
      consumer.ack();
    }
  }

  /** Receive messages over a subscription. */
  public static void main(String... args) throws Exception {
    // set subscriber id, eg. my-sub
    //String subscriptionId = args[0];
	  int totalMessages=1;
	  System.out.println("Starting subscriber!");
	String subscriptionId = "my-sub";
    SubscriptionName subscriptionName = SubscriptionName.create(PROJECT_ID, subscriptionId);
    Subscriber subscriber = null;
    try {
      // create a subscriber bound to the asynchronous message receiver
      subscriber =
          Subscriber.defaultBuilder(subscriptionName, new MessageReceiverExample()).build();
      subscriber.startAsync().awaitRunning();
      // Continue to listen to messages
      while (true) {
        PubsubMessage message = messages.take();
        System.out.println("Subscriber - Message Id: " + message.getMessageId());
        System.out.println("Subscriber - Data: " + message.getData().toStringUtf8());
        System.out.println("Total messages: " + totalMessages++);
      }
    } finally {
      if (subscriber != null) {
        subscriber.stopAsync();
      }
    }
  }
}
