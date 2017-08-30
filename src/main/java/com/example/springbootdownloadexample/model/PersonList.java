package com.example.springbootdownloadexample.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonList {

    @JsonProperty("people")
    private List<Person> list;

    public List<Person> getList () {
        return list;
    }

    public void setList (List<Person> list) {
        this.list = list;
    }
}
