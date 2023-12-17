package kn.vega.pocspringnonreactive.controller;

import kn.vega.pocspringnonreactive.domain.Person;
import kn.vega.pocspringnonreactive.service.PersonService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/non-reactive/db-non-reactive")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson, @PathVariable String id) {
        Person person = personService.updatePerson(updatedPerson, id);
        return person != null ?
                ResponseEntity.ok(person) :
                ResponseEntity.notFound().build();
    }

}
