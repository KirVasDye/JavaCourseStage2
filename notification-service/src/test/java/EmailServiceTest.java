import com.exemple.app.NotificationServiceApplication;
import com.exemple.app.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = NotificationServiceApplication.class)
public class EmailServiceTest {
    @MockitoBean
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    void shouldSendCreatedEmail() {

        emailService.sendCreated("test@mail.com");

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldSendDeletedEmail() {

        emailService.sendDeleted("test@mail.com");

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }
}
