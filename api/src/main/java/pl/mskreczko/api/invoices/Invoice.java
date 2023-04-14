package pl.mskreczko.api.invoices;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    private UUID id;

    @OneToMany
    private List<Ticket> tickets;

    @ManyToOne
    private User user;

    private BigDecimal totalValue;

    private LocalDateTime createdOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(tickets, invoice.tickets) && Objects.equals(user, invoice.user) && Objects.equals(totalValue, invoice.totalValue) && Objects.equals(createdOn, invoice.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tickets, user, totalValue, createdOn);
    }
}
