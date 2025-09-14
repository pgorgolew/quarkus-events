package com.example.agent2;

import com.example.core.EventProcessor;
import com.example.core.Event;
import io.quarkus.logging.Log;

import java.util.Map;

public class Event2Processor implements EventProcessor {

    @Override
    public Event process(Event event) {
        String payload = event.payload();
        Map<String, String> meta = event.metadata();
        Log.infov("Agent2 processing message. Payload: {}. Meta: {}", payload, meta);

        String processedPayload = payload + "[Agent2 Done]";
        return new Event(processedPayload, meta, event.consumer());
    }
}