package com.example.agent2;

import com.example.PubSubResource;
import com.example.core.EventProcessor;
import com.example.core.Event;
import io.quarkus.logging.Log;
import org.jboss.logging.Logger;

import java.util.Map;

public class Event2Processor implements EventProcessor {
    private static final Logger LOG = Logger.getLogger(Event2Processor.class);
    @Override
    public Event process(Event event) {
        String payload = event.payload();
        Map<String, String> meta = event.metadata();
        LOG.info("Agent2 processing message. Payload: %s. Meta: %s".formatted(payload, meta));

        String processedPayload = payload + "[Agent2 Done]";
        return new Event(processedPayload, meta, event.consumer());
    }
}