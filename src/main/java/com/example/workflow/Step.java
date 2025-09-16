package com.example.workflow;

import com.example.core.processor.EventProcessor;
import com.example.core.consumer.EventConsumer;
import com.example.core.publisher.EventPublisher;

public record Step(String id, EventProcessor eventProcessor, EventConsumer eventConsumer, EventPublisher eventPublisher) {
}
