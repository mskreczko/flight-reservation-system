package pl.mskreczko.api.domain.ticket.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.ticket.service.TicketPurchaseService;
import pl.mskreczko.api.domain.ticket.service.TicketSearchService;
import pl.mskreczko.api.exceptions.NoSuchEntityException;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/tickets")
public class TicketController {

    private final TicketSearchService ticketSearchService;
    private final TicketPurchaseService ticketPurchaseService;

    @GetMapping("{flightId}")
    public ResponseEntity<?> getTicketsForFlight(@PathVariable("flightId") UUID flightId) {
        try {
            return ResponseEntity.ok(ticketSearchService.getTicketsForFlight(flightId));
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserTickets() {
        return ResponseEntity.ok(ticketSearchService.getTicketsByUser(UUID.fromString(
                SecurityContextHolder.getContext().getAuthentication().getName())));
    }

    @PostMapping("purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseDto ticketPurchaseDto) {
        try {
            ticketPurchaseService.processPurchase(ticketPurchaseDto);
            return ResponseEntity.ok().build();
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
