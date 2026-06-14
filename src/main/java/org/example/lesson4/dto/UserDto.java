package org.example.lesson4.dto;

import java.time.LocalDate;

public record UserDto(
        Integer id,
        String name,
        String email,
        Integer age,
        LocalDate createAt
) {
}
