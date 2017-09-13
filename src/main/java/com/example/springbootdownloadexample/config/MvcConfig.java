package com.example.springbootdownloadexample.config;

import com.example.springbootdownloadexample.messageconverter.CsvMessageConverter;
import com.example.springbootdownloadexample.messageconverter.PdfMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Do not use path suffix or query parameter.
        // Only evaluate 'Accept' header.
        // Fall back to PDF as a default.
        configurer.favorPathExtension(false)
                //.ignoreUnknownPathExtensions(false)
                //.mediaType("csv", new MediaType("text", "csv"))
                //.useJaf(false)
                .favorParameter(false)
                //.parameterName("media-type")
                .ignoreAcceptHeader(false)
                .defaultContentType(MediaType.valueOf("application/pdf"));
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Do not use suffixes to match URL paths
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Add custom converters here.
        // JSON and XML converters (both supplied by the Jackson library) are in by default.
        converters.add(new CsvMessageConverter());
        converters.add(new PdfMessageConverter());
    }
}
