package com.example.springbootdownloadexample.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public void handleNotAcceptable(HttpServletResponse response,
                                    HttpMediaTypeNotAcceptableException e) {
        serializeException(response, e);
    }

    private void serializeException(HttpServletResponse response,
                                    Exception e) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> exMap = new HashMap<>();
            exMap.put("message", e.getMessage());
            response.getOutputStream().write(mapper.writer().writeValueAsString(exMap).getBytes());
        } catch (Exception ex) {

        }
    }
}
