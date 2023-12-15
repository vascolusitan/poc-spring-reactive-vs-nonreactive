package kn.vega.pocspringreactive.service;

import kn.vega.pocspringreactive.domain.Person;
import kn.vega.pocspringreactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    public Mono<Person> addPerson(Person person) { return personRepository.save(person); }

    public Mono<Person> updatePerson(Person updatedPerson, String id) {
        return personRepository.findById(id)
                .flatMap(person -> {
                    person.setName(updatedPerson.getName());
                    person.setAge(updatedPerson.getAge());

                    return personRepository.save(person);
                });
    }
}
