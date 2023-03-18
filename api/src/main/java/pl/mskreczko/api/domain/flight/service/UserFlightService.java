package pl.mskreczko.api.domain.flight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.flight.Flight;
import pl.mskreczko.api.domain.flight.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFlightService {

    private final FlightRepository flightRepository;

    public List<Flight> getFlights(String departureIcao, String destinationIcao, Optional<String> departureDate) {
        return flightRepository.findAll();
    }
}
