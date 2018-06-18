package com.sergio.example.resources;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Teacher {
    private final String id;
    private final String name;
}
