package pl.mskreczko.api.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.mskreczko.api.domain.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.domain.exceptions.InvalidPasswordException;
import pl.mskreczko.api.domain.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.User;
import pl.mskreczko.api.domain.user.UserRepository;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.role.RoleRepository;
import pl.mskreczko.api.domain.user.service.UserAuthService;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserAuthService userAuthService;

    User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(bCryptPasswordEncoder.encode("ZAQ!2wsx"));
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
    }

    @Test
    void loginUser_throwsInvalidPasswordException() {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        Assertions.assertThrows(InvalidPasswordException.class, () -> userAuthService.loginUser(
                new UserLoginDto("test@test.com", "123")
        ));
    }

    @Test
    void loginUser_throwsNoSuchEntityException() {
        Mockito.doReturn(Optional.empty()).when(userRepository).findByEmail("test@test.com");

        Assertions.assertThrows(NoSuchEntityException.class, () -> userAuthService.loginUser(
                new UserLoginDto("test@test.com", "ZAQ!2wsx")
        ));
    }

    @Test
    void registerUser_throwsAlreadyExistsException() {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> userAuthService.registerUser(
                new UserRegistrationDto("test@test.com", "John", "Doe", "ZAQ!2wsx")
        ));
    }
}
