package com.example;

import com.example.agent1.Event1Processor;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.SubscriberInterface;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import io.quarkiverse.googlecloudservices.pubsub.QuarkusPubSub;
import io.smallrye.common.constraint.NotNull;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestQuery;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Path("/pubsub")
public class PubSubResource {
    private static final Logger LOG = Logger.getLogger(PubSubResource.class);

    private final QuarkusPubSub pubSub;

    public PubSubResource(QuarkusPubSub pubSub) {
        this.pubSub = pubSub;
    }

    @POST
    public String pubsub(@NotNull @RestQuery int count, String payload) throws IOException, InterruptedException {
        // Init a publisher to the topic
        Publisher publisher = pubSub.publisher("test-topic");

        try {
            for (int i = 0; i < count; i++) {
                ByteString data = ByteString.copyFromUtf8(payload + "##" + i);// Create a new message
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);// Publish the message
                ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {// Wait for message submission and log the result
                    public void onSuccess(String messageId) {
                        LOG.infov("published with message id {0}", messageId);
                    }

                    public void onFailure(Throwable t) {
                        LOG.warnv("failed to publish: {0}", t);
                    }
                }, MoreExecutors.directExecutor());
            }
        } finally {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }

        return "Success";
    }
}