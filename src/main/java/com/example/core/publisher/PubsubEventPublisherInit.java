package com.example.core.publisher;

import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;


@ApplicationScoped
@Named("publisher/pubsub")
public class PubsubEventPublisherInit {
    private final QuarkusPubSub quarkusPubSub;

    public PubsubEventPublisherInit(QuarkusPubSub quarkusPubSub) {
        this.quarkusPubSub = quarkusPubSub;
    }

    EventPublisher createEventPublisher(Map<String, Object> properties) {
        return new PubsubEventPublisher(quarkusPubSub, properties.get("topic").toString());
    }
}
