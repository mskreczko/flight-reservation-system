package pl.mskreczko.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.config.jwt.JWTAccessTokenProvider;
import pl.mskreczko.api.domain.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.domain.exceptions.InvalidPasswordException;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.UserRepository;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.role.Role;
import pl.mskreczko.api.domain.user.role.RoleRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService  {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTAccessTokenProvider jwtAccessTokenProvider;

    public void registerUser(UserRegistrationDto userRegistrationDto) throws EntityAlreadyExistsException {
        userRepository.findByEmail(userRegistrationDto.email()).ifPresent((user) -> {
            throw new EntityAlreadyExistsException();
        });

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(userRegistrationDto.email());
        user.setFirstName(userRegistrationDto.firstName());
        user.setLastName(userRegistrationDto.lastName());
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.password()));

        user.getRoles().add(roleRepository.findByName("ROLE_USER"));

        userRepository.save(user);
    }

    public String loginUser(UserLoginDto userLoginDto) {
        final var user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(NoSuchEntityException::new);

        if (!bCryptPasswordEncoder.matches(userLoginDto.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return jwtAccessTokenProvider.getAccessToken(user.getId(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(NoSuchEntityException::new);
    }
}
