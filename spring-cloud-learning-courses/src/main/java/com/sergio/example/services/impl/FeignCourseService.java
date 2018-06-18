package com.sergio.example.services.impl;

import com.sergio.example.resources.Course;
import com.sergio.example.resources.CourseAggregate;
import com.sergio.example.resources.Teacher;
import com.sergio.example.services.CourseService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FeignCourseService implements CourseService {

    private static Map<UUID, Course> courses;
    private final TeachersFeignClient feignClient;

    // Feign clients only need an interface annotated with @FeignClient("service-name")
    // with a method that describes the endpoint that will be consumed
    // and it internally creates a RestTemplate to communicate with the real endpoint.
    @FeignClient("teacher-service")
    public interface TeachersFeignClient {
        @GetMapping(value = "/teachers/{teacherId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        Teacher getById(@PathVariable("teacherId") String teacherId);
    }

    public FeignCourseService(TeachersFeignClient feignClient) {
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
        this.feignClient = feignClient;
    }

    @Override
    public Set<CourseAggregate> getAll() {
        return courses.entrySet().parallelStream()
                .map(c -> {
                    String teacherId = c.getValue().getTeacherId();
                    Teacher teacher = feignClient.getById(teacherId);
                    return CourseAggregate.builder()
                            .course(c.getValue())
                            .teacher(teacher)
                            .build();
                }).collect(Collectors.toSet());
    }
}
