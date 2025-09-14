package com.example.core.listener;

import com.example.core.EventProcessor;
import com.example.core.Event;
import com.example.core.publisher.EventPublisher;
import io.vertx.core.eventbus.EventBus;

import static io.quarkus.arc.ComponentsProvider.LOG;

public class InMemoryEventListener implements EventListener {
    private final EventBus eventBus;
    private final String channel;

    public InMemoryEventListener(EventBus eventBus, String channel) {
        this.eventBus = eventBus;
        this.channel = channel;
    }

    @Override
    public void listen(EventProcessor processor, EventPublisher publisher) {

        //TODO it need to be a blocking handler or at least publishing and sometimes processing
        eventBus.consumer(channel).handler(message -> {
            LOG.infov("Got in-memory message: {0}", message.body());
            Event event = (Event) message.body();

            try {
                Event processedEvent = processor.process(event);
                publisher.publish(processedEvent);
            } catch (Exception ex) {
                event.consumer().nack();
            }
        });
    }
}
