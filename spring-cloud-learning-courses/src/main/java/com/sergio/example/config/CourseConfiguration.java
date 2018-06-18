package com.sergio.example.config;

import com.sergio.example.services.CourseService;
import com.sergio.example.services.impl.SimpleCourseServiceImpl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CourseConfiguration {

    @Bean
    public CourseService courseService(RestTemplate restTemplate) {
        return new SimpleCourseServiceImpl(restTemplate);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
