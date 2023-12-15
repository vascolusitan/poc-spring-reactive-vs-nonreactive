package kn.vega.pocspringnonreactive.repository;

import kn.vega.pocspringnonreactive.domain.Person;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person,String> {
}
