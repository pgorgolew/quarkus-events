package com.example.core.listener;

import com.example.core.EventProcessor;
import com.example.core.Event;
import com.example.core.publisher.EventPublisher;
import io.vertx.core.eventbus.EventBus;

import static io.quarkus.arc.ComponentsProvider.LOG;
import static io.vertx.core.Vertx.vertx;

public class InMemoryEventListener implements EventListener {
    private final EventBus eventBus;
    private final String channel;

    public InMemoryEventListener(EventBus eventBus, String channel) {
        this.eventBus = eventBus;
        this.channel = channel;
    }

    @Override
    public void listen(EventProcessor processor, EventPublisher publisher) {

        eventBus.<Event>consumer(channel).handler(message -> {
            LOG.infov("Got in-memory message: {0}", message.body());
            Event event = message.body();

            vertx().executeBlocking(() -> processor.process(event))
                    .andThen(eventAsyncResult -> publisher.publish(eventAsyncResult.result()))
                    .onFailure(throwable -> event.consumer().nack());
        });
    }
}
