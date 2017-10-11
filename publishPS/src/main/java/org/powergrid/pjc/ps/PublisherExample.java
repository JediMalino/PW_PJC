package org.powergrid.pjc.ps;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;
import java.util.List;

public class PublisherExample {

  static final int MESSAGE_COUNT = 5;

  // use the default project id
  private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();

  //schedule a message to be published, messages are automatically batched
  private static ApiFuture<String> publishMessage(Publisher publisher, String message)
      throws Exception {
    // convert message to bytes
    ByteString data = ByteString.copyFromUtf8(message);
    PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
    return publisher.publish(pubsubMessage);
  }

  /** Publish messages to a topic. */
  public static void main(String... args) throws Exception {
    // topic id, eg. "my-topic"
    //String topicId = args[0];
	String topicId = "my-topic";
    TopicName topicName = TopicName.create(PROJECT_ID, topicId);
    Publisher publisher = null;
    List<ApiFuture<String>> apiFutures = new ArrayList<>();
    
    Thing things[] = new Thing[20000];
    for (int i=0; i<things.length; i++)
    	things[i]=new Thing(ThingState.ENABLE, i+1, "EDP"+i);
    
    try {
      // Create a publisher instance with default settings bound to the topic
      publisher = Publisher.defaultBuilder(topicName).build();
      //for (int i = 0; i < MESSAGE_COUNT; i++) {
      int j=0;
      while (j<50) {
	      for (int i = 0; i < things.length; i++) {
	        //String message = "message-" + i;
	    	  things[i].setState();
	    	  String message=things[i].toString();
	        ApiFuture<String> messageId = publishMessage(publisher, message);
	        apiFutures.add(messageId);
	      }
	      Thread.sleep(5000);
	      j++;
      }
    } finally {
      // Once published, returns server-assigned message ids (unique within the topic)
      List<String> messageIds = ApiFutures.allAsList(apiFutures).get();
      for (String messageId : messageIds) {
        System.out.println(messageId);
      }
      if (publisher != null) {
        // When finished with the publisher, shutdown to free up resources.
        publisher.shutdown();
      }
    }
  }
}
