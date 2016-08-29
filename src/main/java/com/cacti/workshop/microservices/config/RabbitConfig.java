package com.cacti.workshop.microservices.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public final static String queueName = "queue-ejercicio-10";

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    TopicExchange topic() {
        return new TopicExchange("topic-ejercicio-10");
    }

    @Bean
    DirectExchange direct() {
        return new DirectExchange("direct-ejercicio-10");
    }

    @Bean
    Binding binding(final Queue queue, final TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
}
