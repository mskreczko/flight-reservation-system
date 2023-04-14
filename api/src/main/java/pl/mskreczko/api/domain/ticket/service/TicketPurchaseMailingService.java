package pl.mskreczko.api.domain.ticket.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.notifier.EmailNotifier;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TicketPurchaseMailingService {
    private final EmailNotifier emailNotifier;
    private final TicketInvoiceCreator ticketInvoiceCreator;

    public void sendPurchaseNotification(User user, Ticket ticket) throws MessagingException {
        try {
            if (!emailNotifier.sendGenericEmail(user.getEmail(), "Ticket purchase confirmation",
                    "templates/ticket_purchase_confirmation.html", new HashMap<>() {{
                        put("firstName", user.getFirstName());
                        put("lastName", user.getLastName());
                        put("departureAirport", ticket.getFlight().getDepartureAirport().getIcao());
                        put("destinationAirport", ticket.getFlight().getDestinationAirport().getIcao());
                        put("departureDate", ticket.getFlight().getDepartureDate().toString());
                        put("ticketPrice", ticket.getPrice().toString());
                    }}, new File(ticketInvoiceCreator.createInvoice(user, ticket)))) {
                throw new MessagingException();
            }
        } catch (IOException e) {
            throw new MessagingException();
        }
    }
}
