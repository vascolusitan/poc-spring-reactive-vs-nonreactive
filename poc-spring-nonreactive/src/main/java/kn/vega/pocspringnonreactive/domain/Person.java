package kn.vega.pocspringnonreactive.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
}
