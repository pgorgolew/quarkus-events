package com.example.core.consumer;

import com.example.core.publisher.EventPublisher;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import io.quarkus.arc.Arc;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;

import java.util.Map;

@ApplicationScoped
@Named("consumer/pubsub")
public class PubsubEventConsumerInit implements EventConsumerInit {
    private final QuarkusPubSub quarkusPubSub;

    public PubsubEventConsumerInit(QuarkusPubSub quarkusPubSub) {
        this.quarkusPubSub = quarkusPubSub;
    }

    public EventConsumer createEventConsumer(Map<String, Object> properties, EventPublisher eventPublisher) {
        return new PubsubEventConsumer(quarkusPubSub, properties.get("subscription").toString(), eventPublisher);
    }
}
