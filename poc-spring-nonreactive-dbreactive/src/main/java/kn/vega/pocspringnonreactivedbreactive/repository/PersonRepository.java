package kn.vega.pocspringnonreactivedbreactive.repository;

import kn.vega.pocspringnonreactivedbreactive.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person,String> {
}
