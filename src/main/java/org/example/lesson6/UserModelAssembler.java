package org.example.lesson6;

import org.example.lesson4.controller.UserController;
import org.example.lesson4.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler
        implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto dto) {

        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class)
                        .getById(dto.id()))
                        .withSelfRel(),

                linkTo(methodOn(UserController.class)
                        .getAll())
                        .withRel("users"),

                linkTo(methodOn(UserController.class)
                        .delete(dto.id()))
                        .withRel("delete"),

                linkTo(methodOn(UserController.class)
                        .update(dto.id(), null))
                        .withRel("update"));
    }
}
