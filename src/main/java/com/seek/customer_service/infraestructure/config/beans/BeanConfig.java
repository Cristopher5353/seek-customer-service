package com.seek.customer_service.infraestructure.config.beans;

import com.seek.customer_service.application.CustomerServiceImpl;
import com.seek.customer_service.domain.port.in.CustomerUseCase;
import com.seek.customer_service.domain.port.out.CustomerRepositoryPort;
import com.seek.customer_service.domain.port.out.EventBusRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CustomerUseCase customerUseCase(
            CustomerRepositoryPort customerRepositoryPort
            /*EventBusRepositoryPort eventBusRepositoryPort*/) {
        return new CustomerServiceImpl(customerRepositoryPort
                /*eventBusRepositoryPort*/);
    }
}
