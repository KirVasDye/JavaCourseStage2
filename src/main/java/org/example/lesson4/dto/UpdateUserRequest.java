package org.example.lesson4.dto;

public record UpdateUserRequest(
        String name,
        String email,
        Integer age
) {
}
