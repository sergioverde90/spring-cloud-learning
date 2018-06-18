package com.sergio.example.resources;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
public class CourseAggregate {

    private final Course course;
    private final Teacher teacher;

    public UUID getCourseId() {
        return course.getId();
    }

    public String getCourseName() {
        return course.getName();
    }

    public LocalDateTime getStartDate() {
        return course.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return course.getEndDate();
    }
    public String getTeacherId() {
        return teacher.getId();
    }

    public String getTeacherName() {
        return teacher.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseAggregate aggregate = (CourseAggregate) o;
        return Objects.equals(course, aggregate.course) &&
                Objects.equals(teacher, aggregate.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, teacher);
    }

}
