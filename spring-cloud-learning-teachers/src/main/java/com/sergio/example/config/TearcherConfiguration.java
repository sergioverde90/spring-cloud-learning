package com.sergio.example.config;

import com.sergio.example.services.TeacherService;
import com.sergio.example.services.impl.SimpleTeacherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TearcherConfiguration {

    @Bean
    public TeacherService teacherService() {
        return new SimpleTeacherService();
    }
}
