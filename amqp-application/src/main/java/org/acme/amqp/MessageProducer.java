package org.acme.amqp;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/message")
public class MessageProducer {

    @Inject
    @Channel("generated-messages")
    Emitter<String> emitter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage() {
        String message = "Hello from Quarkus!";
        emitter.send(message);
        return "Message sent: " + message;
    }
}
