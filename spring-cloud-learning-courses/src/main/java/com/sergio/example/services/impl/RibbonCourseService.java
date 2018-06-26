package com.sergio.example.services.impl;

import com.sergio.example.repositories.CourseRepository;
import com.sergio.example.resources.Course;
import com.sergio.example.resources.CourseAggregate;
import com.sergio.example.resources.Teacher;
import com.sergio.example.services.CourseService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

public class RibbonCourseService implements CourseService {

    // this restTemplate class is improved by Ribbon,
    // with this we can query directly using the service name.
    // It is configured using the @Configuration class and
    // providing a RestTemplate @Bean annotated with @LoadBalancer
    private final RestTemplate restTemplate;

    private final CourseRepository courseRepository;

    public RibbonCourseService(CourseRepository courseRepository, RestTemplate restTemplate) {
        this.courseRepository = courseRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<CourseAggregate> getAll() {
        String baseTeachersUri = "http://teacher-service";
        Set<Course> courses = courseRepository.getAll();
        return courses.parallelStream()
                .map(c -> {
                    String teacherId = c.getTeacherId();
                    ResponseEntity<Teacher> response = restTemplate
                            .exchange(baseTeachersUri + "/teachers/" + teacherId,
                                    HttpMethod.GET, null, Teacher.class);
                    return CourseAggregate.builder()
                            .course(c)
                            .teacher(response.getBody())
                            .build();
                }).collect(Collectors.toSet());
    }
}
