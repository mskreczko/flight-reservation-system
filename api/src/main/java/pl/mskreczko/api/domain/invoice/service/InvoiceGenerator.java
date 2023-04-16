package pl.mskreczko.api.domain.invoice.service;

import pl.mskreczko.api.domain.invoice.Invoice;

public interface InvoiceGenerator {
    void generateInvoice(String outputFilename, Invoice invoice);
}
