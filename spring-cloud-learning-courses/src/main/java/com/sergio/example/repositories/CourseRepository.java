package com.sergio.example.repositories;

import com.sergio.example.resources.Course;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Repository
public class CourseRepository {


    @Value("${responsiveness.degradation}")
    Boolean degradation;

    private static Map<UUID, Course> courses;

    public CourseRepository() {
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

    public Set<Course> getAll() {
        // simulate process degradation - DON'T DO THIS IN HOME
        if(degradation) {
            simulateUnresponsive();
        }
        return new LinkedHashSet<>(courses.values());
    }

    private void simulateUnresponsive() {
        try { Thread.sleep(5000); }
        catch (InterruptedException e) {
            // Hystrix will interrupt the thread when timeout occurs
            e.printStackTrace();
        }
    }

}
