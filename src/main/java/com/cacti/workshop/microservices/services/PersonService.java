package com.cacti.workshop.microservices.services;

import java.util.List;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.cacti.workshop.microservices.model.Person;
import com.cacti.workshop.microservices.repositories.PersonRepository;
import com.cacti.workshop.microservices.rx.PersonCallable;
import com.cacti.workshop.microservices.rx.PersonObserver;

import rx.Observable;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final TaskExecutor taskExecutor;

    public PersonService(final PersonRepository repository, final TaskExecutor taskExecutor) {
        this.repository = repository;
        this.taskExecutor = taskExecutor;
    }

    public List<Person> findAll() {
        return this.repository.findAll();
    }

    @Async
    public void queueWork(final SseEmitter emitter, final Person person) {

        ((ThreadPoolTaskExecutor) this.taskExecutor).submit(new PersonCallable(person));

        Observable.just(person).subscribe(new PersonObserver(emitter));
    }
}
