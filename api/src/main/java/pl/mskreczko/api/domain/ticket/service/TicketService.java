package pl.mskreczko.api.domain.ticket.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.api.domain.flight.FlightRepository;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.ticket.TicketRepository;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.service.UserAuthService;
import pl.mskreczko.api.invoices.service.HTMLToPDFInvoiceGenerator;
import pl.mskreczko.api.invoices.service.InvoiceService;
import pl.mskreczko.api.notifier.EmailNotifier;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserAuthService userAuthService;
    private final EmailNotifier emailNotifier;
    private final HTMLToPDFInvoiceGenerator htmlToPDFInvoiceGenerator;
    private final InvoiceService invoiceService;

    private boolean isTicketAvailableToBuy(Ticket ticket) {
        return ticket.getNumberOfAvailableTickets() > 0;
    }

    public List<Ticket> getTicketsForFlight(UUID flightId) throws EntityNotFoundException {
        return ticketRepository.findByFlight(flightRepository.findById(flightId).orElseThrow(EntityNotFoundException::new));
    }

    public List<Ticket> getTicketsByUser(UUID userId) {
        return ticketRepository.findByUser((User) userAuthService.loadUserById(userId));
    }

    @Transactional
    public void purchaseTicket(TicketPurchaseDto ticketPurchaseDto) throws EntityNotFoundException, MessagingException {
        final var ticket = ticketRepository.findById(ticketPurchaseDto.ticketId()).orElseThrow(EntityNotFoundException::new);
        if (!isTicketAvailableToBuy(ticket)) {
            throw new EntityNotFoundException();
        }
        var user = (User)userAuthService.loadUserById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));

        user.getTickets().add(ticket);
        ticket.setUser(user);

        ticket.setNumberOfAvailableTickets(ticket.getNumberOfAvailableTickets() - 1);

        final var invoice = invoiceService.createInvoice(user, List.of(ticket));
        final var invoiceTitle = invoiceService.generateInvoiceFilename(invoice);
        htmlToPDFInvoiceGenerator.generateInvoice(invoiceTitle, invoice);

        try {
            if (!emailNotifier.sendGenericEmail(user.getEmail(), "Ticket purchase confirmation",
                    "templates/ticket_purchase_confirmation.html", new HashMap<>() {{
                        put("firstName", user.getFirstName());
                        put("lastName", user.getLastName());
                        put("departureAirport", ticket.getFlight().getDepartureAirport().getIcao());
                        put("destinationAirport", ticket.getFlight().getDestinationAirport().getIcao());
                        put("departureDate", ticket.getFlight().getDepartureDate().toString());
                        put("ticketPrice", ticket.getPrice().toString());
                    }}, new File(invoiceTitle))) {
                throw new MessagingException();
            }
        } catch (IOException e) {
            throw new MessagingException();
        }
    }
}
