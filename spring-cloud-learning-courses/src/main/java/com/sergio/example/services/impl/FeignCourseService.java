package com.sergio.example.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sergio.example.repositories.CourseRepository;
import com.sergio.example.resources.Course;
import com.sergio.example.resources.CourseAggregate;
import com.sergio.example.resources.Teacher;
import com.sergio.example.services.CourseService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class FeignCourseService implements CourseService {

    private final TeachersFeignClient feignClient;

    private final CourseRepository courseRepository;

    // Feign clients only need an interface annotated with @FeignClient("service-name")
    // with a method that describes the endpoint that will be consumed
    // and it internally creates a RestTemplate to communicate with the real endpoint.
    @FeignClient("teacher-service")
    public interface TeachersFeignClient {
        @GetMapping(value = "/teachers/{teacherId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        Teacher getById(@PathVariable("teacherId") String teacherId);
    }

    public FeignCourseService(CourseRepository courseRepository, TeachersFeignClient feignClient) {
        this.courseRepository = courseRepository;
        this.feignClient = feignClient;
    }

    @Override
    // By default, Hystrix uses the same thread pool for
    // all annotated methods. This approach can lead in a thread pool
    // exhaustion when more and more remote calls are made.
    @HystrixCommand(fallbackMethod = "compensate",
        threadPoolKey = "courseThreadPool",
        // complete attribute list -> https://github.com/Netflix/Hystrix/wiki/Configuration
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "100"), // max threads in pool
            @HystrixProperty(name = "maxQueueSize", value = "10"), // max enqueued waiting threads
        })
    public Set<CourseAggregate> getAll() {
        Set<Course> courses = courseRepository.getAll();
        return courses.parallelStream()
                .map(c -> {
                    String teacherId = c.getTeacherId();
                    Teacher teacher = feignClient.getById(teacherId);
                    return CourseAggregate.builder()
                            .course(c)
                            .teacher(teacher)
                            .build();
                }).collect(Collectors.toSet());
    }

    private Set<CourseAggregate> compensate() {
        System.out.println("HYSTRIX FALLBACK DETECTED. COMPENSATING BEHAVIOUR...");
        return Collections.emptySet();
    }
}
