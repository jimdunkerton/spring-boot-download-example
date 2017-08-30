package com.example.springbootdownloadexample.messageconverter;

import com.example.springbootdownloadexample.model.PersonList;
import com.example.springbootdownloadexample.serialization.PdfSerializer;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PdfMessageConverter extends AbstractHttpMessageConverter<PersonList> {

    public PdfMessageConverter() {
        super(MediaType.valueOf("application/pdf"));
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
            ByteArrayInputStream pdf = PdfSerializer.toPdf(people);
            httpOutputMessage.getHeaders().set("Content-Disposition", "attachment; filename=people.pdf");
            IOUtils.copy(pdf, httpOutputMessage.getBody());
        } catch (Exception e) {
            throw new HttpMessageNotWritableException("Cannot write PDF", e);
        }
    }


}
