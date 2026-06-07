package org.example.lesson4.dto;

public record CreateUserRequest(
        String name,
        String email,
        Integer age
) {
}
