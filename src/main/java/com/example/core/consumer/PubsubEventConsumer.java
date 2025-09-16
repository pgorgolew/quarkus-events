package com.example.core.consumer;

import com.example.core.processor.EventProcessor;
import com.example.core.Event;
import com.example.core.publisher.EventPublisher;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.SubscriberInterface;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;

import static io.quarkus.arc.ComponentsProvider.LOG;

public class PubsubEventConsumer extends EventConsumer {
    private final QuarkusPubSub pubSub;
    private final String subscriptionName;

    public PubsubEventConsumer(QuarkusPubSub pubSub, String subscriptionName, EventPublisher eventPublisher) {
        super(eventPublisher);
        this.pubSub = pubSub;
        this.subscriptionName = subscriptionName;
    }

    @Override
    public void listen() {
        MessageReceiver receiver = (message, consumer) -> {
            LOG.infov("Got message from pubsub: {0}", message.getData().toStringUtf8());
            Event event = new Event(message.getData().toStringUtf8(), message.getAttributesMap());

            try{
                publishEvent(event);
                consumer.ack();
            } catch (Exception ex){
                consumer.nack();
            }
        };

        SubscriberInterface subscriber = pubSub.subscriber(subscriptionName, receiver);
        subscriber.startAsync().awaitRunning();
    }

}
