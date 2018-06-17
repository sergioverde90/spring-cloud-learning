package com.sergio.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ControllerApplication {

    // we can retrieve values from Spring Cloud Config
    // server using the @Value annotation
    // One issue to take into account is that Spring Config always
    // has the last version of any property, but Spring Boot only
    // read properties on start-up time. There are several approach
    // to refresh property data.
    @Value("${info.foo}")
    String value;

    @GetMapping("/message")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok(value);
    }
}
