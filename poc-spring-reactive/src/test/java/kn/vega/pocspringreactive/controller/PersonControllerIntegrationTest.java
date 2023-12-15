package kn.vega.pocspringreactive.controller;

import kn.vega.pocspringreactive.domain.Person;
import kn.vega.pocspringreactive.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class PersonControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PersonRepository personRepository;

    private static final String PERSON_REACTIVE_URL = "/api/reactive/db-reactive/persons";

    @AfterEach
    void cleanUp() {
        personRepository.deleteAll().block();
    }

    @Test
    void shouldGetAllPersons() {
        List<Person> personList = List.of(
                Person.builder().name("Vasco").age(28).build(),
                Person.builder().name("Lusitano").age(27).build(),
                Person.builder().name("Rita").age(25).build(),
                Person.builder().name("Lima").age(26).build()
        );
        List<Person> savedPersonList = personRepository.saveAll(personList).collectList().block();

        webTestClient
                .get()
                .uri(PERSON_REACTIVE_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Person.class)
                .hasSize(4)
                .consumeWith(listEntityExchangeResult -> {
                    List<Person> persons = listEntityExchangeResult.getResponseBody();
                    assert persons != null;
                    assert savedPersonList.containsAll(persons);
                });
    }

    @Test
    void shouldUpdatePerson() {
        Person person = personRepository.save(
                Person.builder().name("Vasco").age(28).build()
        ).block();
        person.setAge(29);

        webTestClient
                .put()
                .uri(PERSON_REACTIVE_URL + "/{id}", person.getId())
                .bodyValue(person)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person.class)
                .isEqualTo(person);
    }

    @Test
    void shouldNotUpdatePersonAndReturnNotFound() {
        Long id = 1L;
        Person updatedPerson = Person.builder().id(id).name("Vasco").age(26).build();

        webTestClient
                .put()
                .uri(PERSON_REACTIVE_URL + "/{id}", id)
                .bodyValue(updatedPerson)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldSavePerson() {
        Person person = Person.builder().name("Vasco").age(26).build();

        webTestClient
                .post()
                .uri(PERSON_REACTIVE_URL)
                .bodyValue(person)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Person.class)
                .consumeWith(personEntityExchangeResult -> {
                    Person result = personEntityExchangeResult.getResponseBody();
                    assert result != null;
                    assert result.getId() != null;
                    assert person.getName().equals(result.getName());
                    assert person.getAge() == result.getAge();
                });
    }
}
