package com.cacti.workshop.microservices.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.EmitterException;

import com.cacti.workshop.microservices.model.Person;
import com.cacti.workshop.microservices.services.PersonService;

@RequestMapping("/personas")
@RestController
public class PersonasController {

    private final PersonService service;

    public PersonasController(final PersonService service) {
        this.service = service;
    }

    @GetMapping
    public HttpEntity<List<Person>> getPersons() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping("/stream")
    public SseEmitter add(@RequestBody @Valid final Person person, final BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {

            for (final ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.toString());
            }

            throw new EmitterException(bindingResult.getAllErrors().toString());
        }

        final StopWatch stopWatch = new StopWatch("stream controller");
        stopWatch.start();

        final SseEmitter emitter = new SseEmitter();

        this.service.queueWork(emitter, person);

        emitter.send(stopWatch.shortSummary());

        return emitter;
    }
}
