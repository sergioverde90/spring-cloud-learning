package com.sergio.example.services;

import com.sergio.example.resources.Course;
import com.sergio.example.resources.CourseAggregate;

import java.util.Set;

public interface CourseService {
    Set<CourseAggregate> getAll();
}
