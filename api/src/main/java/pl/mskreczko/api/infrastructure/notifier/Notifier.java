package pl.mskreczko.api.notifier;

public interface Notifier {
    void sendNotification(String receiver, String subject, String body);
}
