package com.example.springbootdownloadexample.model;

import com.example.springbootdownloadexample.serialization.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@JsonPropertyOrder({
        "First Name",
        "Last Name",
        "DOB",
        "Aliases"
})
@Data
@AllArgsConstructor
public class Person {

    @JsonProperty("First Name")
    private String firsName;

    @JsonProperty("Last Name")
    private String lastName;

    @JsonProperty("Aliases")
    private List<String> aliases;

    @JsonProperty("DOB")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;
}
