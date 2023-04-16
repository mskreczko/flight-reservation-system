package pl.mskreczko.api.domain.invoice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.invoice.Invoice;
import pl.mskreczko.api.persistence.invoice.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public String generateInvoiceFilename(Invoice invoice) {
        return invoice.getUser().getId() + "_" + invoice.getTickets().get(0).getId() + ".pdf";
    }

    @Transactional
    public Invoice createInvoice(User user, List<Ticket> tickets) {
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID());
        invoice.setUser(user);
        invoice.setTickets(tickets);
        invoice.setTotalValue(tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        invoice.setCreatedOn(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }
}
