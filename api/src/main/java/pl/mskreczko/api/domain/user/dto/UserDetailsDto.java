package pl.mskreczko.api.domain.user.dto;

import pl.mskreczko.api.domain.ticket.Ticket;

import java.util.List;

public record UserDetailsDto(String email, String firstName, String lastName, List<Ticket> tickets) {
}
