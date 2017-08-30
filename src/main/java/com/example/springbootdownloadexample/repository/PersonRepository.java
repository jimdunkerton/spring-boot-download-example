package com.example.springbootdownloadexample.repository;

import com.example.springbootdownloadexample.model.Person;
import com.example.springbootdownloadexample.model.PersonList;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class PersonRepository {

    private PersonList people = new PersonList(Arrays.asList(
            new Person("John", "Doe", Arrays.asList("Johnny", "John-Boy", "J"), LocalDate.of(1980, 2, 1)),
            new Person("Billy", "Boggs", Arrays.asList("Bill", "Will", "BB"), LocalDate.of(1970, 6, 30))
    ));

    public PersonList getPeople() {
        return this.people;
    }
}
