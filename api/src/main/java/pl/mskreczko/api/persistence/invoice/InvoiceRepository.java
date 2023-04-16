package pl.mskreczko.api.persistence.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mskreczko.api.domain.invoice.Invoice;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
