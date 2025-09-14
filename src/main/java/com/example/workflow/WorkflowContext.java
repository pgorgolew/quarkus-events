package com.example.workflow;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;


@ApplicationScoped
public class WorkflowContext {
    private final Map<String, Step> steps = new HashMap<>();

    public void addStep(Step step) {
        steps.put(step.id(), step);
    }

    public void start(){
        steps.values().forEach(step -> step.eventListener().listen(step.eventProcessor(), step.eventPublisher()));
    }
}