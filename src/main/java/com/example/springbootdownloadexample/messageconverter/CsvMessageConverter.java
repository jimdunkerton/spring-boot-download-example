package com.example.springbootdownloadexample.messageconverter;

import com.example.springbootdownloadexample.model.PersonList;
import com.example.springbootdownloadexample.serialization.CsvSerializer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public class CsvMessageConverter extends AbstractHttpMessageConverter<PersonList> {

    public CsvMessageConverter() {
        super(MediaType.valueOf("text/csv"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return PersonList.class.isAssignableFrom(aClass);
    }

    @Override
    protected PersonList readInternal(Class<? extends PersonList> aClass,
                                        HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Upload not supported");
    }

    @Override
    protected void writeInternal(PersonList people,
                                 HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        try {
            String csv = CsvSerializer.toCsv(people);
            httpOutputMessage.getHeaders().set("Content-Disposition", "attachment; filename=people.csv");
            httpOutputMessage.getBody().write(csv.getBytes());
        } catch (Exception e) {
            throw new HttpMessageNotWritableException("Cannot write PDF", e);
        }

    }
}
