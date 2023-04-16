package pl.mskreczko.api.persistence.flight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.mskreczko.api.domain.airport.Airport;
import pl.mskreczko.api.domain.flight.Flight;

import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends PagingAndSortingRepository<Flight, UUID> {
    Optional<Flight> findById(UUID id);
    Page<Flight> findByDepartureAirportAndDestinationAirport(Airport departureAirport, Airport destinationAirport,
                                                             PageRequest pageRequest);
}
