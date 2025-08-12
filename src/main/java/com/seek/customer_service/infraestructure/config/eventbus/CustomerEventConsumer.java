package com.seek.customer_service.infraestructure.config.eventbus;

import com.azure.messaging.servicebus.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*@Component
public class CustomerEventConsumer {

    @Value("${azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${azure.servicebus.queue-name}")
    private String queueName;

    private ServiceBusProcessorClient processorClient;

    @PostConstruct
    public void startProcessor() {
        processorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .queueName(queueName)
                .processMessage(context -> {
                    String messageBody = context.getMessage().getBody().toString();
                    System.out.println("Mensaje recibido desde azure service bus: " + messageBody);
                })
                .processError(errorContext -> {
                    System.err.println("Error en el procesamiento: " + errorContext.getException());
                })
                .buildProcessorClient();

        processorClient.start();
    }
}*/
