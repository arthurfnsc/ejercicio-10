package com.cacti.workshop.microservices.rx;

import java.util.concurrent.Callable;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.cacti.workshop.microservices.config.RabbitConfig;
import com.cacti.workshop.microservices.model.Person;

public class PersonCallable implements Callable<Person> {

    private final Person person;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PersonCallable(final Person person) {
        this.person = person;
    }

    @Override
    public Person call() throws Exception {

        System.out.println("3");

        this.rabbitTemplate.convertAndSend(RabbitConfig.queueName, this.person);

        return this.person;
    }
}
