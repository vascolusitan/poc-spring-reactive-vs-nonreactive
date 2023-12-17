package kn.vega.pocspringnonreactivedbreactive.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("person")
@Builder
public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
}
