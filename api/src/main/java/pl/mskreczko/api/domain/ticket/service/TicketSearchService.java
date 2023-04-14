package pl.mskreczko.api.domain.ticket.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.flight.FlightRepository;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.ticket.TicketRepository;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.service.UserAuthService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TicketSearchService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserAuthService userAuthService;

    public List<Ticket> getTicketsForFlight(UUID flightId) throws EntityNotFoundException {
        return ticketRepository.findByFlight(flightRepository.findById(flightId).orElseThrow(EntityNotFoundException::new));
    }

    public List<Ticket> getTicketsByUser(UUID userId) {
        return ticketRepository.findByUser((User) userAuthService.loadUserById(userId));
    }
}
