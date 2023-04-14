package pl.mskreczko.api.notifier;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    private String fillInParams(String templateFilename, Map<String, String> params) throws IOException {
        String content = readFile(templateFilename);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return content;
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
           content = fillInParams(templateFilename, params);
        } catch (IOException e) {
            return false;
        }

        message.setContent(content, "text/html; charset=utf-8");
        mailSender.send(message);
        return true;
    }

    public boolean sendGenericEmail(String recipient, String subject, String templateFilename, Map<String, String> params,
                                    File attachment) throws MessagingException, IOException {

        String content;
        try {
            content = fillInParams(templateFilename, params);
        } catch (IOException e) {
            return false;
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress("sender@example.com"));
            helper.setTo(recipient);
            helper.setSubject(subject);

            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(attachment);
            helper.addAttachment(attachment.getName(), file);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
