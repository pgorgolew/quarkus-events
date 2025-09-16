package com.example.core.consumer;

import com.example.core.Event;
import com.example.core.processor.EventProcessor;
import com.example.core.publisher.EventPublisher;
import io.smallrye.common.constraint.NotNull;

public abstract class EventConsumer {
    @NotNull private final EventPublisher eventPublisher;

    public EventConsumer(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    protected final void publishEvent(Event event){
        eventPublisher.publish(event);
    }

    public abstract void listen();
}
