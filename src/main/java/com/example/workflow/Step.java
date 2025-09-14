package com.example.workflow;

import com.example.core.EventProcessor;
import com.example.core.listener.EventListener;
import com.example.core.publisher.EventPublisher;

public record Step(String id, EventProcessor eventProcessor, EventListener eventListener, EventPublisher eventPublisher) {
}
