package pl.mskreczko.api.domain.flight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.flight.FlightRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFlightService {

    private final FlightRepository flightRepository;

    public Page<Flight> getAllFlights(Optional<Integer> pageNumber) {
        if (pageNumber.isEmpty()) {
            return flightRepository.findAll(PageRequest.of(0, 10));
        }
        return flightRepository.findAll(PageRequest.of(pageNumber.get(), 10));
    }

    public Page<Flight> getFlightsByCriteria(String departureIcao, String destinationIcao,
                                             Optional<String> departureDate, Optional<Integer> pageNumber) {
        if (pageNumber.isEmpty()) {
            return flightRepository.findAll(PageRequest.of(1, 10));
        }
        return flightRepository.findAll(PageRequest.of(pageNumber.get(), 10));
    }

    public Optional<Flight> getSingleFlight(UUID flightId) {
        return flightRepository.findById(flightId);
    }
}
