import com.exemple.app.NotificationServiceApplication;
import dto.Operation;
import dto.UserEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@Testcontainers
@SpringBootTest(classes = NotificationServiceApplication.class)
public class UserEventListenerTest {

    @Container
    static KafkaContainer kafka =
            new KafkaContainer(
                    DockerImageName.parse(
                            "apache/kafka:3.7.0"));

    @MockitoBean
    JavaMailSender sender;

    @Autowired
    KafkaTemplate<String, UserEvent> template;

    @Test
    void shouldSendMailWhenEventReceived() {

        template.send(
                "user-events",
                new UserEvent(
                        Operation.CREATED,
                        "test@mail.com"
                )
        );

        await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() ->
                        verify(sender)
                                .send(any(SimpleMailMessage.class)));
    }
}
