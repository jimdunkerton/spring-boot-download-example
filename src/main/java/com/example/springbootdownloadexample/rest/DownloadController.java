package com.example.springbootdownloadexample.rest;

import com.example.springbootdownloadexample.model.PersonList;
import com.example.springbootdownloadexample.repository.PersonRepository;
import com.example.springbootdownloadexample.serialization.CsvSerializer;
import com.example.springbootdownloadexample.serialization.PdfSerializer;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAuthority('USER')")
public class DownloadController {

    private PersonRepository repo;

    public DownloadController(@Autowired PersonRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/download/entities",
            method = RequestMethod.GET,
            produces = {"application/json", "text/csv", "application/pdf"})
    public PersonList getAsEntities() throws Exception {
        return this.repo.getPeople();
    }

    @RequestMapping(value = "/download/isr",
            method = RequestMethod.GET,
            produces = {"text/csv", "application/pdf"})
    public ResponseEntity<InputStreamResource> getAsInputStreamResourceEntity(@RequestHeader("Accept") String accept) throws Exception {
        PersonList people = this.repo.getPeople();
        HttpHeaders responseHeaders = new HttpHeaders();
        if (accept.equals("text/csv")) {
            responseHeaders.set("Content-Disposition", "attachment; filename=isr.csv");
            String csv = CsvSerializer.toCsv(people);
            return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(csv.getBytes())), responseHeaders, HttpStatus.OK);
        } else {
            responseHeaders.set("Content-Disposition", "attachment; filename=isr.pdf");
            ByteArrayInputStream pdf = PdfSerializer.toPdf(people);
            return new ResponseEntity<>(new InputStreamResource(pdf), responseHeaders, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/download/bytes",
            method = RequestMethod.GET,
            produces = {"text/csv", "application/pdf"})
    public ResponseEntity<byte[]> getAsByteArrayEntity(@RequestHeader("Accept") String accept) throws Exception {
        PersonList people = this.repo.getPeople();
        HttpHeaders responseHeaders = new HttpHeaders();
        if (accept.equals("text/csv")) {
            responseHeaders.set("Content-Disposition", "attachment; filename=bytes.csv");
            String csv = CsvSerializer.toCsv(people);
            return new ResponseEntity<>(csv.getBytes(), responseHeaders, HttpStatus.OK);
        } else {
            responseHeaders.set("Content-Disposition", "attachment; filename=bytes.pdf");
            ByteArrayInputStream pdf = PdfSerializer.toPdf(people);
            return new ResponseEntity<>(IOUtils.toByteArray(pdf), responseHeaders, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/download/async",
            method = RequestMethod.GET,
            produces = {"text/csv", "application/pdf"})
    public ResponseEntity<StreamingResponseBody> getAsStreamingResponseBody(@RequestHeader("Accept") String accept) throws Exception {
        PersonList people = this.repo.getPeople();
        HttpHeaders responseHeaders = new HttpHeaders();
        if (accept.equals("text/csv")) {
            responseHeaders.set("Content-Disposition", "attachment; filename=async.csv");
            String csv = CsvSerializer.toCsv(people);
            return new ResponseEntity<>(
                    outputStream -> outputStream.write(csv.getBytes()),
                    responseHeaders,
                    HttpStatus.OK);
        } else {
            responseHeaders.set("Content-Disposition", "attachment; filename=async.pdf");
            ByteArrayInputStream pdf = PdfSerializer.toPdf(people);
            return new ResponseEntity<>(
                    outputStream -> outputStream.write(IOUtils.toByteArray(pdf)),
                    responseHeaders,
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/download/servletresponse",
            method = RequestMethod.GET,
            produces = {"text/csv", "application/pdf"})
    public void writeToServletResponse(@RequestHeader("Accept") String accept,
                                       HttpServletResponse response) throws Exception {
        try {
            PersonList people = this.repo.getPeople();
            if (accept.equals("text/csv")) {
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=servletresponse.csv");
                String csv = CsvSerializer.toCsv(people);
                response.getOutputStream().write(csv.getBytes());
            } else {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=servletresponse.pdf");
                ByteArrayInputStream pdf = PdfSerializer.toPdf(people);
                response.getOutputStream().write(IOUtils.toByteArray(pdf));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
