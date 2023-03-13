package pl.mskreczko.api.domain.ticket.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.api.domain.ticket.TicketRepository;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.service.UserAuthService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TicketPurchaseService {

    private final TicketRepository ticketRepository;
    private final UserAuthService userAuthService;

    @Transactional
    public void purchaseTicket(TicketPurchaseDto ticketPurchaseDto) throws EntityNotFoundException {
        final var ticket = ticketRepository.findById(ticketPurchaseDto.ticketId()).orElseThrow(EntityNotFoundException::new);
        var user = (User)userAuthService.loadUserById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
        user.getTickets().add(ticket);
        ticket.setUser(user);
    }
}
