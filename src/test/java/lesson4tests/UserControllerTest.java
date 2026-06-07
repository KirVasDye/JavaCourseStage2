package lesson4tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lesson4.Demo;
import org.example.lesson4.controller.UserController;
import org.example.lesson4.dto.CreateUserRequest;
import org.example.lesson4.dto.UpdateUserRequest;
import org.example.lesson4.dto.UserDto;
import org.example.lesson4.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = Demo.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsers() throws Exception {

        UserDto user = new UserDto(
                1,
                "Kirill",
                "kirill@mail.com",
                22,
                LocalDate.now()
        );

        when(userService.getAll())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Kirill"));
    }

    @Test
    void getUserById() throws Exception {

        UserDto user = new UserDto(
                1,
                "Kirill",
                "kirill@mail.com",
                22,
                LocalDate.now()
        );

        when(userService.getById(1))
                .thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kirill"));
    }

    @Test
    void createUser() throws Exception {

        CreateUserRequest request =
                new CreateUserRequest(
                        "Kirill",
                        "kirill@mail.com",
                        22
                );

        UserDto response = new UserDto(
                1,
                "Kirill",
                "kirill@mail.com",
                22,
                LocalDate.now()
        );

        when(userService.create(any(CreateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Kirill"));
    }

    @Test
    void updateUser() throws Exception {

        UpdateUserRequest request =
                new UpdateUserRequest(
                        "Updated",
                        "updated@mail.com",
                        25
                );

        UserDto response = new UserDto(
                1,
                "Updated",
                "updated@mail.com",
                25,
                LocalDate.now()
        );

        when(userService.update(eq(1), any(UpdateUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void deleteUser() throws Exception {

        doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}
