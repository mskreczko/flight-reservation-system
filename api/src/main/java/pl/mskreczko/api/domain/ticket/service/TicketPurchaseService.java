package pl.mskreczko.api.domain.ticket.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.ticket.TicketRepository;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.service.UserAuthService;
import pl.mskreczko.api.exceptions.NoSuchEntityException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketPurchaseService {
    private final TicketRepository ticketRepository;
    private final UserAuthService userAuthService;
    private final TicketPurchaseMailingService purchaseMailingService;

    private boolean isTicketAvailableToBuy(Ticket ticket) {
        return ticket.getNumberOfAvailableTickets() > 0;
    }

    @Transactional
    public void purchaseTicket(User user, Ticket ticket) throws EntityNotFoundException {
        if (!isTicketAvailableToBuy(ticket)) {
            throw new EntityNotFoundException();
        }

        user.getTickets().add(ticket);
        ticket.setUser(user);
        ticket.decreaseNumberOfTickets();
    }

    public void processPurchase(TicketPurchaseDto ticketPurchaseDto) throws NoSuchEntityException, MessagingException {
        var ticket = ticketRepository.findById(ticketPurchaseDto.ticketId()).orElseThrow(NoSuchEntityException::new);
        var user = (User)userAuthService.loadUserById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        purchaseTicket(user, ticket);
        purchaseMailingService.sendPurchaseNotification(user, ticket);
    }
}
