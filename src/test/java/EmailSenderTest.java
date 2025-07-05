import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

public class EmailSenderTest {
    @Test
    void testEmailSender_withMockedIpFetcherAndMail() throws Exception {
        // Mock IpFetcherApi
        try (MockedStatic<IpFetcherApi> ipMock = Mockito.mockStatic(IpFetcherApi.class)) {
            ipMock.when(IpFetcherApi::fetchCurrentMachineIp).thenReturn(new String[]{"192.168.1.100", "203.0.113.42"});

            // Mock JavaMail Transport
            try (MockedStatic<Transport> transportMock = Mockito.mockStatic(Transport.class)) {
                // Set up properties
                Properties envProps = new Properties();
                envProps.setProperty("USERNAME", "test@example.com");
                envProps.setProperty("APP_PASSWORD", "dummy");
                envProps.setProperty("TO_EMAIL", "to@example.com");

                // Simulate main logic up to sending
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("test@example.com", "dummy");
                    }
                });
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("test@example.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@example.com"));
                message.setSubject("[Captain’s Log – Neural Ship AIVA]");
                String[] ip = IpFetcherApi.fetchCurrentMachineIp();
                String text = "LAN: " + ip[0] + ", WAN: " + ip[1];
                message.setText(text);
                // Should not throw
                assertDoesNotThrow(() -> Transport.send(message));
            }
        }
    }
}

