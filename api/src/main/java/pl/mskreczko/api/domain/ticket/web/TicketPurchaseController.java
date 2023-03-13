package pl.mskreczko.api.domain.ticket.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.domain.ticket.dto.TicketPurchaseDto;
import pl.mskreczko.api.domain.ticket.service.TicketPurchaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/tickets")
public class TicketPurchaseController {

    private final TicketPurchaseService ticketPurchaseService;

    @PostMapping("purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody TicketPurchaseDto ticketPurchaseDto) {
        try {
            ticketPurchaseService.purchaseTicket(ticketPurchaseDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
