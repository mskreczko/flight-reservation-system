package pl.mskreczko.api.domain.flight;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.mskreczko.api.domain.airport.Airport;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "icao_departure")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "icao_destination")
    private Airport destinationAirport;

    private LocalDateTime departureDate;
}
