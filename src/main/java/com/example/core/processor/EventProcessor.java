package com.example.core.processor;

import com.example.core.Event;
import com.example.core.publisher.EventPublisher;

public abstract class EventProcessor {
    private final EventPublisher eventPublisher;

    protected EventProcessor(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    protected final void publishEvent(Event event){
        eventPublisher.publish(event);
    }

    abstract Event process(Event event);

    public final void processEvent(Event event){
        Event processedEvent = process(event);
        publishEvent(processedEvent);
    }
}
