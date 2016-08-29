package com.cacti.workshop.microservices.rx;

import java.io.IOException;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.cacti.workshop.microservices.model.Person;

import rx.Observer;

public class PersonObserver implements Observer<Person> {

    private final SseEmitter emitter;

    public PersonObserver(final SseEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onCompleted() {
        System.out.println("2-1");
        this.emitter.complete();
    }

    @Override
    public void onError(final Throwable e) {
        System.out.println("2-2");
        this.emitter.completeWithError(e);
    }

    @Override
    public void onNext(final Person person) {

        System.out.println("2-3");

        try {
            this.emitter.send(person);
        } catch (final IOException e) {
            throw new RuntimeException();
        }
    }
}
