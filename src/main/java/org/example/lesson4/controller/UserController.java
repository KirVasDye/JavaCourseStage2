package org.example.lesson4.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson4.dto.CreateUserRequest;
import org.example.lesson4.dto.UpdateUserRequest;
import org.example.lesson4.dto.UserDto;
import org.example.lesson4.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public UserDto create(
            @RequestBody CreateUserRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public UserDto update(
            @PathVariable Integer id,
            @RequestBody UpdateUserRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
