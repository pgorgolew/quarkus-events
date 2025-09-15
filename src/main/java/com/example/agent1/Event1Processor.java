package com.example.agent1;

import com.example.agent3.Event3Processor;
import com.example.core.EventProcessor;
import com.example.core.Event;
import io.quarkus.logging.Log;
import org.jboss.logging.Logger;

import java.util.Map;

public class Event1Processor implements EventProcessor {
    private static final Logger LOG = Logger.getLogger(Event1Processor.class);

    @Override
    public Event process(Event event) {
        String payload = event.payload();
        Map<String, String> meta = event.metadata();
        LOG.info("Agent1 processing message. Payload: %s. Meta: %s".formatted(payload, meta));

        String processedPayload = payload + "[Agent1 Done]";
        return new Event(processedPayload, meta, event.consumer());
    }
}
