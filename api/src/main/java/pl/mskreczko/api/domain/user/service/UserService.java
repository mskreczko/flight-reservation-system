package pl.mskreczko.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.UserRepository;
import pl.mskreczko.api.domain.user.dto.UserDetailsDto;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsDto getUserDetails(UUID id) throws NoSuchEntityException {
        final var user = userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
        return new UserDetailsDto(user.getEmail(), user.getFirstName(), user.getLastName(), user.getTickets());
    }

    public void deleteUser(UUID id) throws NoSuchEntityException {
        final var user = userRepository.findById(id).orElseThrow(NoSuchEntityException::new);
        for (var ticket : user.getTickets()) {
            ticket.setUser(null);
        }
        userRepository.delete(user);
    }
}
