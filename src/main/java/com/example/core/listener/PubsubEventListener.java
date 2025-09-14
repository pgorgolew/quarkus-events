package com.example.core.listener;

import com.example.core.EventProcessor;
import com.example.core.Event;
import com.example.core.publisher.EventPublisher;
import com.example.core.consumer.PubsubEventConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.SubscriberInterface;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;

import static io.quarkus.arc.ComponentsProvider.LOG;

public class PubsubEventListener implements EventListener {
    private final QuarkusPubSub pubSub;
    private final String subscriptionName;

    public PubsubEventListener(QuarkusPubSub pubSub, String subscriptionName) {
        this.pubSub = pubSub;
        this.subscriptionName = subscriptionName;
    }

    @Override
    public void listen(EventProcessor processor, EventPublisher publisher) {
        // Subscribe to PubSub
        MessageReceiver receiver = (message, consumer) -> {
            LOG.infov("Got message from pubsub: {0}", message.getData().toStringUtf8());
            Event event = new Event(message.getData().toStringUtf8(), message.getAttributesMap(), PubsubEventConsumer.of(consumer));

            try{
                Event processedEvent = processor.process(event);
                publisher.publish(processedEvent);
            } catch (Exception ex){
                event.consumer().nack();
            }
        };

        SubscriberInterface subscriber = pubSub.subscriber(subscriptionName, receiver);
        subscriber.startAsync().awaitRunning();
    }
}
