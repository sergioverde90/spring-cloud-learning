package com.sergio.example.controllers;

import com.sergio.example.resources.CourseAggregate;
import com.sergio.example.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    // we can retrieve values from Spring Cloud Config
    // server using the @Value annotation
    // One issue to take into account is that Spring Config always
    // has the last version of any property, but Spring Boot only
    // read properties on start-up time. There are several approach
    // to refresh property data.
    @Value("${info.foo}")
    String value;

    @Autowired
    public CourseController(CourseService service) {
        this.courseService = service;
    }

    @GetMapping
    public ResponseEntity<Set<CourseAggregate>> courses() {
        Set<CourseAggregate> courses = this.courseService.getAll();
        return ResponseEntity.ok(courses);
    }
}
