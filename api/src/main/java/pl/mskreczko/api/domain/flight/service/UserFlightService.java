package pl.mskreczko.api.domain.flight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.airport.AirportRepository;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.flight.FlightRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Cacheable(value = "users", key="#pageNumber")
    public Page<Flight> getAllFlights(Integer pageNumber) {
        return flightRepository.findAll(PageRequest.of(pageNumber, 5));
    }

    public Page<Flight> getFlightsByCriteria(String departureIcao, String destinationIcao,
                                             Optional<String> departureDate, Integer pageNumber) throws NoSuchEntityException {
        final var departureAirport = airportRepository.findById(departureIcao).orElseThrow(NoSuchEntityException::new);
        final var destinationAirport = airportRepository.findById(destinationIcao).orElseThrow(NoSuchEntityException::new);
        return flightRepository.findByDepartureAirportAndDestinationAirport(departureAirport, destinationAirport, PageRequest.of(pageNumber, 5));
    }

    public Optional<Flight> getSingleFlight(UUID flightId) {
        return flightRepository.findById(flightId);
    }
}
