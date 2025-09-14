package com.example.core.listener;

import com.example.core.EventProcessor;
import com.example.core.publisher.EventPublisher;

public interface EventListener {
    void listen(EventProcessor processor, EventPublisher publisher);
}
