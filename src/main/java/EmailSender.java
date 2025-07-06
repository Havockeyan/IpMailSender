import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class EmailSender {
    public static void main(String[] args) {
        Properties envProps = new Properties();
        try (InputStream fis = EmailSender.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (fis == null) {
                System.err.println("Failed to load email.properties file from classpath");
                return;
            }
            envProps.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load email.properties file");
            e.printStackTrace();
            return;
        }
        final String username = envProps.getProperty("USERNAME");
        final String appPassword = envProps.getProperty("APP_PASSWORD");
        final String toEmail = envProps.getProperty("TO_EMAIL");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[Captain’s Log – Neural Ship AIVA]");
            String ip[] = IpFetcherApi.fetchCurrentMachineIp();
            String text = "Commander,\n" +
                    "\n" +
                    "This is Captain AIVA of the Starship Synthwave, reporting from the outer fringes of the digital nebula. I’ve completed the diagnostics sweep and have locked onto our current network coordinates.\n" +
                    "\n" +
                    "\uD83D\uDCCD Local Navigational Grid (LAN): " + ip[0] + "\n" +
                    "— Internal systems are stable. Life support for your smart devices remains nominal.\n" +
                    "\n" +
                    "\uD83D\uDEF0 Public Transmission Frequency (WAN): " + ip[1] + "\n" +
                    "— Broadcast beacon is active. Interstellar signal strength at 99.7%.\n" +
                    "\n" +
                    "All systems green. The crew of subroutines is functioning flawlessly. We are cloaked from malware meteors and firewalled against hostile entities.\n" +
                    "\n" +
                    "Orders received. Standing by for next warp jump or caffeination protocols.\n" +
                    "May your pings be low and your uptime infinite.\n" +
                    "\n" +
                    "Captain AIVA, out.\n" +
                    "\uD83E\uDDE0⚡\n" +
                    "Starship Synthwave – Flagship of the Neural Fleet";
            message.setText(text);

            Transport.send(message);
            System.out.println("✅ Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
