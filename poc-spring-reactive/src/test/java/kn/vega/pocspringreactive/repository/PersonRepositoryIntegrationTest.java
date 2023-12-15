package kn.vega.pocspringreactive.repository;

import kn.vega.pocspringreactive.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@DataR2dbcTest
@ActiveProfiles("test")
public class PersonRepositoryIntegrationTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    void shouldGetAllPersons() {
        List<Person> personList = List.of(
                Person.builder().name("Vasco").age(28).build(),
                Person.builder().name("Lusitano").age(27).build(),
                Person.builder().name("Rita").age(25).build(),
                Person.builder().name("Lima").age(26).build()
        );
        personRepository.saveAll(personList).collectList().block();

        Flux<Person> personFlux = personRepository.findAll();

        StepVerifier.create(personFlux)
                .expectNextSequence(personList)
                .verifyComplete();
    }

    @Test
    void shouldUpdatePerson() {
        int newAge = 29;
        Person person = personRepository.save(
                Person.builder().name("Vasco").age(28).build()
        ).block();
        person.setAge(newAge);

        Mono<Person> updatedPersonMono = personRepository.save(person);

        StepVerifier.create(updatedPersonMono)
                .assertNext(updatedPerson -> {
                    assert updatedPerson != null;
                    assert person.getId() == updatedPerson.getId();
                    assert updatedPerson.getAge() == newAge;
                })
                .verifyComplete();
    }

    @Test
    void shouldSavePerson() {
        Person person = Person.builder().name("Vasco").age(26).build();

        Mono<Person> savedPersonMono = personRepository.save(person);

        StepVerifier.create(savedPersonMono)
                .assertNext(savedPerson -> {
                    assert savedPerson != null;
                    assert savedPerson.getId() != null;
                    assert savedPerson.getName().equals(person.getName()) &&
                            savedPerson.getAge() == person.getAge();
                })
                .verifyComplete();
    }

}
