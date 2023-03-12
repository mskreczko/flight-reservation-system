package pl.mskreczko.api.domain.user.dto;

public record UserRegistrationDto(String email, String firstName, String lastName, String password) {
}
