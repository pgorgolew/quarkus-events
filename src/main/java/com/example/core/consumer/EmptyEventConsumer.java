package com.example.core.consumer;

public class EmptyEventConsumer implements EventConsumer {

    @Override
    public void nack() {
        // do nothing
    }

    @Override
    public void ack() {
        // do nothing
    }
}
