package com.example.core.consumer;

public interface EventConsumer {
    void nack();
    void ack();
}
