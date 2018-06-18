package com.sergio.example.services.impl;

import com.sergio.example.resources.Teacher;
import com.sergio.example.services.TeacherService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleTeacherService implements TeacherService {

    private static Map<String, Teacher> teachers;

    public SimpleTeacherService() {
        teachers = new HashMap<>();
        Teacher teacher = Teacher.builder()
                .id("1")
                .name("fake teacher")
                .build();
        teachers.put("1", teacher);
        teachers = Collections.unmodifiableMap(teachers);
    }

    @Override
    public Teacher getById(String teacherId) {
        return teachers.get(teacherId);
    }
}
