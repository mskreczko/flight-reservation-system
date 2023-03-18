package pl.mskreczko.api.domain.flight.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mskreczko.api.domain.flight.service.UserFlightService;

import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/api/v1/unauthenticated/flights")
@RestController
@RequiredArgsConstructor
public class UserFlightController {

    private final UserFlightService userFlightService;

    @GetMapping("find")
    public ResponseEntity<?> findFlights(@RequestParam("departure_icao") String departureIcao,
                                         @RequestParam("destination_icao") String destinationIcao,
                                         @RequestParam("departure_date") String departureDate,
                                         @RequestParam("return_date") Optional<String> returnDate) {
        return ResponseEntity.ok(userFlightService.getFlights(departureIcao, destinationIcao, returnDate));
    }
}
