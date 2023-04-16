package pl.mskreczko.api.domain.ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.invoice.service.HTMLToPDFInvoiceGenerator;
import pl.mskreczko.api.domain.invoice.service.InvoiceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketInvoiceCreator {

    private final InvoiceService invoiceService;
    private final HTMLToPDFInvoiceGenerator htmlToPDFInvoiceGenerator;

    public String createInvoice(User user, Ticket ticket) {
        final var invoice = invoiceService.createInvoice(user, List.of(ticket));
        final var invoiceTitle = invoiceService.generateInvoiceFilename(invoice);
        htmlToPDFInvoiceGenerator.generateInvoice(invoiceTitle, invoice);
        return invoiceTitle;
    }
}
