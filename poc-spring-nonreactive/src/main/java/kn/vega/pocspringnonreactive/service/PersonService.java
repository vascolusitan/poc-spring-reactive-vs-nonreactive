package kn.vega.pocspringnonreactive.service;

import kn.vega.pocspringnonreactive.domain.Person;
import kn.vega.pocspringnonreactive.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Person updatedPerson, String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
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
