package com.seek.customer_service.infraestructure.config.eventbus;

import com.azure.messaging.servicebus.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/*@Service
public class AzureServiceBusSender {
    private final ServiceBusSenderAsyncClient senderAsyncClient;

    @Value("${azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${azure.servicebus.queue-name}")
    private String queueName;

    public AzureServiceBusSender(ServiceBusSenderAsyncClient senderAsyncClient) {
        this.senderAsyncClient = senderAsyncClient;
    }

    public Mono<Void> sendMessageAsync(String messageBody) {
        ServiceBusMessage message = new ServiceBusMessage(messageBody);
        return senderAsyncClient.sendMessage(message);
    }
}*/
