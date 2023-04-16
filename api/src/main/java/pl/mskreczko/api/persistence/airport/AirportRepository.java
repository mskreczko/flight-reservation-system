package pl.mskreczko.api.persistence.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mskreczko.api.domain.airport.Airport;

public interface AirportRepository extends JpaRepository<Airport, String> {
}
