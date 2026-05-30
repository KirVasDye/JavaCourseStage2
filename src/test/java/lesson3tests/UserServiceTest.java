package lesson3tests;

import org.example.lesson2.dao.UserDao;
import org.example.lesson2.model.User;
import org.example.lesson2.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUser() {

        userService.createUser(
                1,
                "Kirill",
                "kirill@test.com",
                25
        );

        verify(userDao).save(any(User.class));
    }

    @Test
    void createUser_IllegalIdThrow() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userService.createUser(
                                null,
                                "Kirill",
                                "kirill@test.com",
                                25
                        )
                );

        assertEquals(
                "id пользователя не может быть пустым",
                exception.getMessage()
                );

        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_IllegalNameThrow() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userService.createUser(
                                1,
                                null,
                                "kirill@test.com",
                                25
                        )
                );

        assertEquals(
                "Имя пользователя не может быть пустым",
                exception.getMessage()
                );

        verifyNoInteractions(userDao);
    }

    @Test
    void createUser_IllegalAgeThrow() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> userService.createUser(
                                1,
                                "Kirill",
                                "kirill@test.com",
                                null
                        )
                );

        assertEquals(
                "Возраст указан некорректно",
                exception.getMessage()
        );
    }

    @Test
    void getUser_shouldReturnUser() {

        User user = User.builder()
                .id(1)
                .name("Kirill")
                .email("kirill@test.com")
                .age(25)
                .build();

        when(userDao.findById(1))
                .thenReturn(user);

        User result = userService.getUser(1);

        assertEquals(user, result);

        verify(userDao).findById(1);
    }

    @Test
    void getAllUsers_shouldReturnUsers() {

        List<User> users = List.of(
                User.builder().id(1).name("Alex").email("alex@test.com").age(28).build(),
                User.builder().id(2).name("John").email("lohn@test.com").age(27).build()
        );

        when(userDao.findAll())
                .thenReturn(users);

        List<User> result =
                userService.getAllUsers();

        assertEquals(2, result.size());

        verify(userDao).findAll();
    }

    @Test
    void updateUser_shouldCallDao() {

        User user = User.builder()
                .id(1)
                .name("Alex")
                .email("alex@test.com")
                .age(28)
                .build();

        userService.updateUser(user);

        verify(userDao).update(user);
    }

    @Test
    void deleteUser_shouldCallDao() {

        userService.deleteUser(1);

        verify(userDao).delete(1);

    }
}
