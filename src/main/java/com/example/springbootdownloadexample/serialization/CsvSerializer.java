package com.example.springbootdownloadexample.serialization;

import com.example.springbootdownloadexample.model.Person;
import com.example.springbootdownloadexample.model.PersonList;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvSerializer {

    public static String toCsv(PersonList people) throws Exception {
        CsvMapper mapper = new CsvMapper();
        CsvSchema personSchema = mapper.schemaFor(Person.class).withHeader();
        return mapper.writer(personSchema).writeValueAsString(people.getList());
    }
}
