package com.example.agent3;

import com.example.core.EventProcessor;
import com.example.core.Event;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.Map;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class Event3Processor implements EventProcessor {

    @Override
    public Event process(Event event) {
        String payload = event.payload();
        Map<String, String> meta = event.metadata();
        Log.infov("Agent2 processing message. Payload: {}. Meta: {}", payload, meta);


        if (payload.contains("FAIL")) {
            LOG.error("FAIL in agent3");
            throw new RuntimeException("Business error in Agent3Processor");
        }

        String processedPayload = payload + "[Agent3 Done]";
        return new Event(processedPayload, meta, event.consumer());
    }
}