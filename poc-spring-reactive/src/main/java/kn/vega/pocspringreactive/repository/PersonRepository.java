package kn.vega.pocspringreactive.repository;

import kn.vega.pocspringreactive.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person,String> {
}
