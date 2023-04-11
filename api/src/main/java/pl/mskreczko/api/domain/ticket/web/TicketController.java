package pl.mskreczko.api.domain.ticket.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.ticket.service.TicketService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("{flightId}")
    public ResponseEntity<?> getTicketsForFlight(@PathVariable("flightId") UUID flightId) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsForFlight(flightId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserTickets() {
        return ResponseEntity.ok(ticketService.getTicketsByUser(UUID.fromString(
                SecurityContextHolder.getContext().getAuthentication().getName())));
    }

    @PostMapping("purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseDto ticketPurchaseDto) {
        try {
            ticketService.purchaseTicket(ticketPurchaseDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
