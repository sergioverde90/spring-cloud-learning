package com.sergio.example.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ServiceApplication {

    // we can retrieve values from Spring Cloud Config
    // server using the @Value annotation
    @Value("${info.foo}")
    String value;

    @PostConstruct
    public void onInit() {
        System.out.println("value = " + value);
    }
}
