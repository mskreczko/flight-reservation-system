package pl.mskreczko.api.domain.user.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mskreczko.api.config.jwt.JWTAccessTokenProvider;
import pl.mskreczko.api.domain.exceptions.AccountNotActivatedException;
import pl.mskreczko.api.domain.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.domain.exceptions.InvalidPasswordException;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.UserRepository;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.role.Role;
import pl.mskreczko.api.domain.user.role.RoleRepository;
import pl.mskreczko.api.notifier.EmailNotifier;
import pl.mskreczko.api.tokens.EmailVerificationTokenService;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService  {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTAccessTokenProvider jwtAccessTokenProvider;
    private final EmailNotifier emailNotifier;
    private final EmailVerificationTokenService emailVerificationTokenService;

    public void registerUser(UserRegistrationDto userRegistrationDto) throws EntityAlreadyExistsException, MessagingException {
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

        if (!emailNotifier.sendGenericEmail(user.getEmail(), "Account activation", "templates/account_activation.html",
                new HashMap<>() {{
                    put("name", user.getFirstName());
                    put("token", emailVerificationTokenService.createToken(user));
                }}))
        {
            throw new MessagingException();
        }
    }

    public String loginUser(UserLoginDto userLoginDto) throws InvalidPasswordException, AccountNotActivatedException {
        final var user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(NoSuchEntityException::new);
        if (!user.isEnabled()) {
            throw new AccountNotActivatedException();
        }

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

    public UserDetails loadUserById(UUID userId) throws NoSuchElementException {
        return userRepository.findById(userId).orElseThrow(NoSuchEntityException::new);
    }
}
