package com.example.core.publisher;

import com.example.core.consumer.EventConsumer;
import com.example.core.processor.EventProcessor;

import java.util.Map;

public interface EventPublisherInit {
    EventPublisher createEventPublisher(Map<String, Object> properties);
}
