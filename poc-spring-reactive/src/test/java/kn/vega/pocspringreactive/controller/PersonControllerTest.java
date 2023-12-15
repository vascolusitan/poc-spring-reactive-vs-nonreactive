package kn.vega.pocspringreactive.controller;

import kn.vega.pocspringreactive.domain.Person;
import kn.vega.pocspringreactive.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PersonController.class)
@AutoConfigureWebTestClient
public class PersonControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    PersonService personService;

    private static final String PERSON_REACTIVE_URL = "/api/reactive/db-reactive/persons";

    @Test
    void shouldGetAllPersons() {
        List<Person> personList = List.of(
            Person.builder().id(1L).name("Vasco").age(28).build(),
            Person.builder().id(2L).name("Lusitano").age(27).build(),
            Person.builder().id(3L).name("Rita").age(25).build(),
            Person.builder().id(4L).name("Lima").age(26).build()
        );
        when(personService.findAll()).thenReturn(Flux.fromIterable(personList));

        webTestClient
                .get()
                .uri(PERSON_REACTIVE_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Person.class)
                .consumeWith(listEntityExchangeResult -> {
                    List<Person> persons = listEntityExchangeResult.getResponseBody();
                    assert personList.equals(persons);
                });
    }

    @Test
    void shouldUpdatePerson() {
        Long id = 1L;
        Person updatedPerson = Person.builder().id(id).name("Vasco").age(26).build();
        when(personService.updatePerson(updatedPerson, String.valueOf(id)))
                .thenReturn(Mono.just(updatedPerson));

        webTestClient
                .put()
                .uri(PERSON_REACTIVE_URL + "/{id}", id)
                .bodyValue(updatedPerson)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person.class)
                .isEqualTo(updatedPerson);
    }

    @Test
    void shouldNotUpdatePersonAndReturnNotFound() {
        Long id = 1L;
        Person updatedPerson = Person.builder().id(id).name("Vasco").age(26).build();
        when(personService.updatePerson(updatedPerson, String.valueOf(id)))
                .thenReturn(Mono.empty());

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
        Long id = 1L;
        Person person = Person.builder().id(id).name("Vasco").age(26).build();
        when(personService.addPerson(person))
                .thenReturn(Mono.just(person));

        webTestClient
                .post()
                .uri(PERSON_REACTIVE_URL)
                .bodyValue(person)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Person.class)
                .isEqualTo(person);
    }

}
