package com.sergio.example.config;

import com.sergio.example.repositories.CourseRepository;
import com.sergio.example.services.CourseService;
import com.sergio.example.services.impl.FeignCourseService;
import com.sergio.example.services.impl.RibbonCourseService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CourseConfiguration {

    @Bean
    public CourseService courseService(
            CourseRepository courseRepository,
            FeignCourseService.TeachersFeignClient teachersFeignClient) {
        return new FeignCourseService(courseRepository, teachersFeignClient);
    }

    @Bean
    public CourseRepository courseRepository() {
        return new CourseRepository();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
