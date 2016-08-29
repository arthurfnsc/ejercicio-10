package com.cacti.workshop.microservices;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.cacti.workshop.microservices.config.RabbitConfig;
import com.cacti.workshop.microservices.model.Person;
import com.cacti.workshop.microservices.repositories.PersonRepository;

@Component
public class Listener {

    PersonRepository repository;

    private final RabbitTemplate rabbitTemplate;

    public Listener(final PersonRepository repository, final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitConfig.queueName)
    public void listen(final Message message) {

        System.out.println("4");

        final Person person = (Person) this.rabbitTemplate.receiveAndConvert(RabbitConfig.queueName);

        this.repository.save(person);
    }
}
