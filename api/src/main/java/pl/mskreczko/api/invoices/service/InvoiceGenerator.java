package pl.mskreczko.api.invoices.service;

import pl.mskreczko.api.invoices.Invoice;

public interface InvoiceGenerator {
    void generateInvoice(String outputFilename, Invoice invoice);
}
