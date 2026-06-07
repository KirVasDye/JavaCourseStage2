package lesson3tests;

import org.example.lesson2.dao.UserDao;
import org.example.lesson2.dao.UserDaoImpl;
import org.example.lesson2.model.User;
import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class UserDaoIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("JavaCourse")
                    .withUsername(System.getenv("DB_USERNAME"))
                    .withPassword(System.getenv("DB_PASSWORD"));

    @BeforeAll
    static void beforeAll() {

        System.setProperty(
                "hibernate.connection.url",
                postgreSQLContainer.getJdbcUrl()
        );

        System.setProperty(
                "hibernate.connection.username",
                postgreSQLContainer.getUsername()
        );

        System.setProperty(
                "hibernate.connection.password",
                postgreSQLContainer.getPassword()
        );
    }

    private final UserDao dao =
            new UserDaoImpl();

    @Test
    void save_shouldPersistUser() {

        User user = User.builder()
                .id(1)
                .name("Kirill")
                .email("kirill@test.com")
                .age(25)
                .createdAt(LocalDate.now())
                .build();

        dao.save(user);

        User found =
                dao.findById(1);

        assertNotNull(found);
        assertEquals("Kirill", found.getName());
    }

    @Test
    void delete_shouldRemoveUser() {

        User user = User.builder()
                .id(2)
                .name("John")
                .email("john@test.com")
                .age(30)
                .createdAt(LocalDate.now())
                .build();

        dao.save(user);

        dao.delete(2);

        User found =
                dao.findById(2);

        assertNull(found);
    }

    @AfterEach
    void cleanup() {

        dao.findAll()
                .forEach(user ->
                        dao.delete(user.getId()));
    }
}
