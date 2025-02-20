package org.acme.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.nio.charset.StandardCharsets;

@Path("/message")
public class MessageProducer {

    @Inject
    @Channel("generated-messages")
    Emitter<String> emitter;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @WithSpan
    public String sendMessage() {
        try {
            LargeMessage message = new LargeMessage(1_000_000); // 1MB object
            String jsonPayload = objectMapper.writeValueAsString(message);
            emitter.send(jsonPayload);
            return "Message sent with payload size: " + jsonPayload.getBytes(StandardCharsets.UTF_8).length + " bytes";
        } catch (JsonProcessingException e) {
            return "Error serializing payload: " + e.getMessage();
        }
    }
}
