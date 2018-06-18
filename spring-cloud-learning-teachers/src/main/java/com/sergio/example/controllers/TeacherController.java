package com.sergio.example.controllers;

import com.sergio.example.resources.Teacher;
import com.sergio.example.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping(value = "/{teacherId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Teacher> getById(@PathVariable("teacherId") String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        return ResponseEntity.ok(teacher);
    }
}
