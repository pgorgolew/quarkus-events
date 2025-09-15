package com.example.agent3;

import com.example.agent2.Event2Processor;
import com.example.core.EventProcessor;
import com.example.core.Event;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;


import java.util.Map;


@ApplicationScoped
public class Event3Processor implements EventProcessor {
    private static final Logger LOG = Logger.getLogger(Event3Processor.class);

    @Override
    public Event process(Event event) {
        String payload = event.payload();
        Map<String, String> meta = event.metadata();
        LOG.info("Agent3 processing message. Payload: %s. Meta: %s".formatted(payload, meta));


        if (payload.contains("FAIL")) {
            LOG.error("FAIL in agent3");
            throw new RuntimeException("Business error in Agent3Processor");
        }

        String processedPayload = payload + "[Agent3 Done]";
        return new Event(processedPayload, meta, event.consumer());
    }
}