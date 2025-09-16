package com.example.workflow;

import com.example.agent1.Event1Processor;
import com.example.agent2.Event2Processor;
import com.example.agent3.Event3Processor;
import com.example.core.Event;
import com.example.core.processor.EventProcessor;
import com.example.core.publisher.EventPublisher;
import com.example.core.consumer.EventConsumer;
import com.example.core.publisher.PubsubEventPublisher;
import com.example.core.consumer.PubsubEventConsumer;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import io.quarkus.runtime.Startup;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

@Startup
@ApplicationScoped
public class StartupBean {

    private final WorkflowConfig config;
    private final QuarkusPubSub pubSub;
    private final EventBus eventBus;
    private final WorkflowContext  workflowContext;

    public StartupBean(WorkflowConfig config, QuarkusPubSub pubSub, EventBus eventBus, WorkflowContext workflowContext) {
        this.config = config;
        this.pubSub = pubSub;
        this.eventBus = eventBus;
        this.workflowContext = workflowContext;
    }

    @PostConstruct
    void init() throws IOException {
        pubSub.createTopic("test-topic");
        pubSub.createSubscription("test-topic", "test-subscription");
        eventBus.registerDefaultCodec(Event.class, new EventMessageCodec());

        config.steps().forEach((stepName, stepConfig) -> {
            EventProcessor eventProcessor = createEventProcessor(stepConfig);
            EventConsumer eventConsumer = createEventReceiver(stepConfig.source());
            EventPublisher eventPublisher = createEventPublisher(stepConfig.target());
            workflowContext.addStep(new Step(stepName, eventProcessor, eventConsumer, eventPublisher));
        });

        workflowContext.start();
    }

    private EventProcessor createEventProcessor(WorkflowConfig.Step stepConfig) {
        return switch (stepConfig.type()) {
            case TRANSFORM1 -> new Event1Processor();
            case TRANSFORM2 -> new Event2Processor();
            case TRANSFORM3 -> new Event3Processor();
        };
    }

    private EventConsumer createEventReceiver(WorkflowConfig.Step.Source sourceConfig) {
        return switch (sourceConfig.type()) {
            case PUBSUB -> new PubsubEventConsumer(pubSub, sourceConfig.value());
            case IN_MEMORY -> new InMemoryEventConsumer(eventBus, sourceConfig.value());
        };
    }

    private EventPublisher createEventPublisher(WorkflowConfig.Step.Target targetConfig) {
        return switch (targetConfig.type()) {
            case PUBSUB -> new PubsubEventPublisher(pubSub, targetConfig.value());
            case IN_MEMORY -> new InMemoryEventPublisher(eventBus, targetConfig.value());
        };
    }
}
