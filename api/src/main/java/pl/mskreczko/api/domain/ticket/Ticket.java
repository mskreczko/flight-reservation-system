package pl.mskreczko.api.domain.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.user.User;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TravelClass travelClass;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flight=" + flight +
                ", user=" + user +
                ", price=" + price +
                ", travelClass=" + travelClass +
                '}';
    }
}
