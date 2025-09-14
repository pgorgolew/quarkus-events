package com.example.core.consumer;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

public class PubsubEventConsumer implements EventConsumer {

    private final AckReplyConsumer ackReplyConsumer;

    private PubsubEventConsumer(AckReplyConsumer ackReplyConsumer) {
        this.ackReplyConsumer = ackReplyConsumer;
    }

    public static PubsubEventConsumer of(AckReplyConsumer consumer){
        return new PubsubEventConsumer(consumer);
    }

    @Override
    public void nack() {
        ackReplyConsumer.nack();
    }

    @Override
    public void ack() {
        ackReplyConsumer.ack();
    }
}
