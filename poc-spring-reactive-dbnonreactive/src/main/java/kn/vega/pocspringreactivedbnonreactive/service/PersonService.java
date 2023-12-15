package kn.vega.pocspringreactivedbnonreactive.service;

import kn.vega.pocspringreactivedbnonreactive.domain.Person;
import kn.vega.pocspringreactivedbnonreactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<Person> findAll() {
        Iterable<Person> persons = personRepository.findAll();
        return Flux.fromIterable(
                persons
        );
    }

    public Mono<Person> addPerson(Person person) {
        return Mono.just(
                personRepository.save(person)
        );
    }

    public Mono<Person> updatePerson(Person updatedPerson, String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        Mono<Person> result = Mono.empty();
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(updatedPerson.getName());
            person.setAge(updatedPerson.getAge());
            result = Mono.just(person);
        }
        return result;
    }
}
