package kn.vega.pocspringnonreactivedbreactive.service;

import kn.vega.pocspringnonreactivedbreactive.domain.Person;
import kn.vega.pocspringnonreactivedbreactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll().collectList().block();
    }

    public Person addPerson(Person person) {
        return personRepository.save(person).block();
    }

    public Person updatePerson(Person updatedPerson, String id) {
        Optional<Person> optionalPerson = personRepository.findById(id).blockOptional();
        Person result = null;
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(updatedPerson.getName());
            person.setAge(updatedPerson.getAge());
            result = person;
        }
        return result;
    }
}
