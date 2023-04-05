package pl.mskreczko.api.notifier;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailNotifier implements Notifier {

    private final JavaMailSender mailSender;

    @Override
    public void sendNotification(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendVerificationEmail(String recipient, String firstName, String token) throws MessagingException  {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, recipient);
        message.setSubject("Account activation");

        String content = "<h1>Hello, " + firstName + "\n<p>Click this link to activate your account</p>\n" +
                "<a href=\"http://localhost:3000/activateAccount?token=\"" + token + ">http://localhost:3000/activateAccount?token="
                + token + "</a>";

        message.setContent(content, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
