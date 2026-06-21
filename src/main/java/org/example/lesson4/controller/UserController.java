package org.example.lesson4.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.lesson4.dto.CreateUserRequest;
import org.example.lesson4.dto.UpdateUserRequest;
import org.example.lesson4.dto.UserDto;
import org.example.lesson4.service.UserService;
import org.example.lesson6.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API для работы с пользователями")
public class UserController {

    private final UserService service;
    private final UserModelAssembler assembler;

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAll() {
        List<EntityModel<UserDto>> users =
                service.getAll()
                        .stream()
                        .map(assembler::toModel)
                        .toList();

        return CollectionModel.of(
                users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel()
        );
    }

    @Operation(summary = "Получить пользователя по id")
    @GetMapping("/{id}")
    public EntityModel<UserDto> getById(@PathVariable Integer id) {
        return assembler.toModel(service.getById(id));
    }

    @Operation(summary = "Создать пользователя")
    @PostMapping
    public EntityModel<UserDto> create(
            @RequestBody CreateUserRequest request) {
        return assembler.toModel(service.create(request));
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public EntityModel<UserDto> update(
            @PathVariable Integer id,
            @RequestBody UpdateUserRequest request) {

        return assembler.toModel(service.update(id, request));
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
