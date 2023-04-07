package pl.mskreczko.api.notifier;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailNotifier implements Notifier {

    private final JavaMailSender mailSender;

    private String readFile(String filename) throws IOException {
        File resource = new ClassPathResource(filename).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }

    @Override
    public void sendNotification(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public boolean sendGenericEmail(String recipient, String subject, String templateFilename, Map<String, String> params) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, recipient);
        message.setSubject(subject);

        String content;

        try {
            content = readFile(templateFilename);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                content = content.replace("${" + entry.getKey() + "}", entry.getValue());
            }
        } catch (IOException e) {
            return false;
        }

        message.setContent(content, "text/html; charset=utf-8");
        mailSender.send(message);
        return true;
    }
}
