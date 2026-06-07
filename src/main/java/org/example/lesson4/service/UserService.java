package org.example.lesson4.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.example.lesson2.model.User;
import org.example.lesson4.dto.CreateUserRequest;
import org.example.lesson4.dto.UpdateUserRequest;
import org.example.lesson4.dto.UserDto;
import org.example.lesson4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    private UserDto toDto(@Nonnull User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt()
        );
    }

    public List<UserDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto getById(Integer id) {
        User user = repository.findById(id)
                .orElseThrow();

        return toDto(user);
    }

    public UserDto create(CreateUserRequest request) {

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .age(request.age())
                .createdAt(LocalDate.now())
                .build();

        return toDto(repository.save(user));
    }

    public UserDto update(Integer id,
                          UpdateUserRequest request) {

        User user = repository.findById(id)
                .orElseThrow();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setAge(request.age());

        return toDto(repository.save(user));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
