package com.example.core.publisher;

import com.example.core.Event;
import io.vertx.core.eventbus.EventBus;

public class InMemoryEventPublisher implements EventPublisher {

    private final String channel;
    private final EventBus eventBus;

    public InMemoryEventPublisher(EventBus eventBus, String channel) {
        this.channel = channel;
        this.eventBus = eventBus;
    }

    @Override
    public void publish(Event event) {
        eventBus.publish(channel, event);
    }
}
