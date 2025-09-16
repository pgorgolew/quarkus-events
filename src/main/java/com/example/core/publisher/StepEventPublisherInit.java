package com.example.core.publisher;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;


@ApplicationScoped
@Named("publisher/step")
public class StepEventPublisherInit {
    EventPublisher createEventPublisher(Map<String, Object> properties) {
        return new StepEventPublisher(properties.get("stepName").toString());
    }
}
