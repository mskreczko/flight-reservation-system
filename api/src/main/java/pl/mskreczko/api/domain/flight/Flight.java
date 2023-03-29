package pl.mskreczko.api.domain.flight;

import jakarta.persistence.*;
import lombok.*;
import pl.mskreczko.api.domain.airport.Airport;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "flights")
public class Flight implements Serializable {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "icao_departure")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "icao_destination")
    private Airport destinationAirport;

    private LocalDateTime departureDate;

    private String airline;

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureAirport=" + departureAirport +
                ", destinationAirport=" + destinationAirport +
                ", departureDate=" + departureDate +
                '}';
    }
}
