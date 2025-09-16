package com.example.core.processor;

import com.example.core.Event;
import com.example.core.consumer.EventConsumer;
import com.example.core.publisher.EventPublisher;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

public interface EventProcessorInit {
    EventProcessor createEventProcessor(Map<String, Object> properties, EventPublisher eventPublisher);

}
