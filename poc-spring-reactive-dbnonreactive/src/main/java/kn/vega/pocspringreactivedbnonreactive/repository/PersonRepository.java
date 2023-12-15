package kn.vega.pocspringreactivedbnonreactive.repository;

import kn.vega.pocspringreactivedbnonreactive.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person,String> {
}
