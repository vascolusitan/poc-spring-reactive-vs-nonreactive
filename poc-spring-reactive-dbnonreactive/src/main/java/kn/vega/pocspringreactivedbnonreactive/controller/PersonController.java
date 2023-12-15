package kn.vega.pocspringreactivedbnonreactive.controller;

import kn.vega.pocspringreactivedbnonreactive.domain.Person;
import kn.vega.pocspringreactivedbnonreactive.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reactive/db-non-reactive")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/persons")
    public Flux<Person> getAllPersons() {
        return personService.findAll();
    }

    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/persons/{id}")
    public Mono<ResponseEntity<Person>> updatePerson(@RequestBody Person updatedPerson, @PathVariable String id) {
        return personService.updatePerson(updatedPerson, id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
