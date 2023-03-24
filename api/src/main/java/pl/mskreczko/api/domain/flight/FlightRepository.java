package pl.mskreczko.api.domain.flight;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends PagingAndSortingRepository<Flight, UUID> {
    Optional<Flight> findById(UUID id);
}
