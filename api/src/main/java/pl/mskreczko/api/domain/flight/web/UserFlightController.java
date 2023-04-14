package pl.mskreczko.api.domain.flight.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.domain.flight.service.UserFlightService;
import pl.mskreczko.api.exceptions.NoSuchEntityException;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/v1/unauthenticated/flights")
@RestController
@RequiredArgsConstructor
public class UserFlightController {

    private final UserFlightService userFlightService;

    @GetMapping
    public ResponseEntity<?> getAllFlights(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(userFlightService.getAllFlights(pageNumber));
    }

    @GetMapping("find")
    public ResponseEntity<?> findFlights(@RequestParam("departure_icao") String departureIcao,
                                         @RequestParam("destination_icao") String destinationIcao,
                                         @RequestParam("departure_date") String departureDate,
                                         @RequestParam("return_date") Optional<String> returnDate,
                                         @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber) throws NoSuchEntityException {
        return ResponseEntity.ok(userFlightService.getFlightsByCriteria(departureIcao, destinationIcao, returnDate,
                pageNumber));
    }

    @GetMapping("{flight_id}")
    public ResponseEntity<?> getSingleFlight(@PathVariable("flight_id")UUID flightId) {
        return ResponseEntity.of(userFlightService.getSingleFlight(flightId));
    }
}
