package com.sergio.example.services.impl;

import com.sergio.example.resources.Course;
import com.sergio.example.resources.CourseAggregate;
import com.sergio.example.resources.Teacher;
import com.sergio.example.services.CourseService;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RibbonCourseService implements CourseService {

    private static Map<UUID, Course> courses;

    // this restTemplate class is improved by Ribbon,
    // with this we can query directly using the service name.
    // It is configured using the @Configuration class and
    // providing a RestTemplate @Bean annotated with @LoadBalancer
    private final RestTemplate restTemplate;

    public RibbonCourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        Map<UUID, Course> allCourses = new HashMap<>();
        Course course = Course.builder()
                .id(UUID.randomUUID())
                .name("Spring Boot 2 - from scratch")
                .teacherId("1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .build();
        allCourses.put(course.getId(), course);
        courses = Collections.unmodifiableMap(allCourses);
    }

    @Override
    public Set<CourseAggregate> getAll() {
        String baseTeachersUri = "http://teacher-service";

        return courses.entrySet().parallelStream()
                .map(c -> {
                    String teacherId = c.getValue().getTeacherId();
                    ResponseEntity<Teacher> response = restTemplate
                            .exchange(baseTeachersUri + "/teachers/" + teacherId,
                                    HttpMethod.GET, null, Teacher.class);
                    return CourseAggregate.builder()
                            .course(c.getValue())
                            .teacher(response.getBody())
                            .build();
                }).collect(Collectors.toSet());
    }
}
