package com.example.core.consumer;

import com.example.core.publisher.EventPublisher;

import java.util.Map;

public interface EventConsumerInit {
    EventConsumer createEventConsumer(Map<String, Object> properties, EventPublisher eventPublisher);
}
