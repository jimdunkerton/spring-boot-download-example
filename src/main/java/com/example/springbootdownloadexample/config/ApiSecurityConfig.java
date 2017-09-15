package com.example.springbootdownloadexample.config;

import com.example.springbootdownloadexample.exceptionhandling.ExceptionHandling;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                            ExceptionHandling.serializeException(httpServletResponse, e);
                        }
                    )
                    .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                            ExceptionHandling.serializeException(httpServletResponse, e);
                        }
                )
                .and()
                .csrf()
                .disable();
    }
}
