package org.acme.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @WithSpan
    @Incoming("messages")
    public void consume(String message) {
        try {
            // Deserialize JSON into LargeMessage object
            LargeMessage receivedMessage = objectMapper.readValue(message, LargeMessage.class);
            System.out.println("Received message: ID = " + receivedMessage.getId());
            System.out.println("Name: " + receivedMessage.getName());
            System.out.println("Description: " + receivedMessage.getDescription());
            System.out.println("Numbers array length: " + receivedMessage.getNumbers().length);
            System.out.println("Large text size: " + receivedMessage.getLargeText().length());
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
