package pl.mskreczko.api.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.user.User;

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
    private User user;

    private Double price;

    private TravelClass travelClass;
}
