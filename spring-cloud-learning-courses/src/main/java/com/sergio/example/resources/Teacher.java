package com.sergio.example.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public class Teacher {
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(name, teacher.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
