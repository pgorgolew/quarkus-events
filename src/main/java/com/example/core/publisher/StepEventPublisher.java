package com.example.core.publisher;

import com.example.core.Event;
import com.example.core.processor.EventProcessor;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableBean;
import io.quarkus.arc.InjectableInstance;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.inject.literal.NamedLiteral;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StepEventPublisher implements EventPublisher {
    private final EventProcessor nextProcessor;

    public StepEventPublisher(String stepName) {
        InjectableInstance<EventProcessor> eventProcessorInjectableBean = Arc.container().select(EventProcessor.class, NamedLiteral.of(stepName));

        if (!eventProcessorInjectableBean.isResolvable()){
            throw new RuntimeException("Failed to find next event processor");
        }
        this.nextProcessor = eventProcessorInjectableBean.get();
    }

    @Override
    public void publish(Event event) {
        nextProcessor.processEvent(event);
    }
}
