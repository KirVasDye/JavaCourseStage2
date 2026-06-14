package org.example.lesson4.service;

import dto.Operation;
import dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.lesson2.model.User;
import org.example.lesson4.dto.CreateUserRequest;
import org.example.lesson4.dto.UpdateUserRequest;
import org.example.lesson4.dto.UserDto;
import org.example.lesson4.dto.UserMapper;
import org.example.lesson4.repository.UserRepository;
import org.example.lesson5.producers.UserEventProducer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan("org.example.lesson5.producers")
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserEventProducer producer;

    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto getById(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapper.toDto(user);
    }

    public UserDto create(CreateUserRequest request) {

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .age(request.age())
                .createdAt(LocalDate.now())
                .build();

        repository.save(user);

        producer.send(
                new UserEvent(
                        Operation.CREATED,
                        user.getEmail()
                )
        );

        return mapper.toDto(user);
    }

    public UserDto update(Integer id,
                          UpdateUserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        mapper.updateEntity(user, request);

        return mapper.toDto(repository.save(user));
    }

    @Transactional
    public void delete(Integer id) {

        User user = repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

        repository.delete(user);

        producer.send(
                new UserEvent(
                        Operation.DELETED,
                        user.getEmail()
                )
        );
    }
}
