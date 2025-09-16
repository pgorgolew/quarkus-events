package com.example.core.publisher;

import com.example.core.Event;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import jakarta.annotation.PreDestroy;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.quarkus.arc.ComponentsProvider.LOG;

public class PubsubEventPublisher implements EventPublisher {

    private final Publisher publisher;

    public PubsubEventPublisher(QuarkusPubSub pubSub, String topic) {
        try {
            publisher = pubSub.publisher(topic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publish(Event event) {
        ByteString data = ByteString.copyFromUtf8(event.payload());
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).putAllAttributes(event.metadata()).build();
        try {
            publisher.publish(pubsubMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void shutdown() {
        publisher.shutdown();
    }
}
