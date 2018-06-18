package com.sergio.example.resources;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
public class Course {
    private final UUID id;
    private final String name;
    private final String teacherId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(teacherId, course.teacherId) &&
                Objects.equals(startDate, course.startDate) &&
                Objects.equals(endDate, course.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, teacherId, startDate, endDate);
    }
}
