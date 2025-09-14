package com.example.workflow;

import com.example.agent1.Event1Processor;
import com.example.agent2.Event2Processor;
import com.example.agent3.Event3Processor;
import com.example.core.Event;
import com.example.core.EventMessageCodec;
import com.example.core.EventProcessor;
import com.example.core.publisher.EventPublisher;
import com.example.core.listener.EventListener;
import com.example.core.publisher.InMemoryEventPublisher;
import com.example.core.listener.InMemoryEventListener;
import com.example.core.publisher.PubsubEventPublisher;
import com.example.core.listener.PubsubEventListener;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import io.quarkus.runtime.Startup;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
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
            EventListener eventListener = createEventReceiver(stepConfig.source());
            EventPublisher eventPublisher = createEventPublisher(stepConfig.target());
            workflowContext.addStep(new Step(stepName, eventProcessor, eventListener, eventPublisher));
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

    private EventListener createEventReceiver(WorkflowConfig.Step.Source sourceConfig) {
        return switch (sourceConfig.type()) {
            case PUBSUB -> new PubsubEventListener(pubSub, sourceConfig.value());
            case IN_MEMORY -> new InMemoryEventListener(eventBus, sourceConfig.value());
        };
    }

    private EventPublisher createEventPublisher(WorkflowConfig.Step.Target targetConfig) {
        return switch (targetConfig.type()) {
            case PUBSUB -> new PubsubEventPublisher(pubSub, targetConfig.value());
            case IN_MEMORY -> new InMemoryEventPublisher(eventBus, targetConfig.value());
        };
    }
}
