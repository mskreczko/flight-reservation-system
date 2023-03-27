package pl.mskreczko.api.domain.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByFlight(Flight flight);
    List<Ticket> findByUser(User user);
}
