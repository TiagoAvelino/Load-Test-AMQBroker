package org.acme.amqp;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("messages")
    public void consume(String message) {
        System.out.println("Message received: " + message);
    }
}