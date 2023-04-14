package pl.mskreczko.api.domain.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.exceptions.AccountNotActivatedException;
import pl.mskreczko.api.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.exceptions.InvalidPasswordException;
import pl.mskreczko.api.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.service.UserAuthService;
import pl.mskreczko.api.tokens.EmailVerificationTokenService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    private final UserAuthService userAuthService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            final var token = userAuthService.loginUser(userLoginDto);
            return ResponseEntity.ok(token);
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        } catch (AccountNotActivatedException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        } catch (InvalidPasswordException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            userAuthService.registerUser(userRegistrationDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (EntityAlreadyExistsException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PatchMapping("verifyAccount")
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token) {
        try {
            if (!emailVerificationTokenService.verifyToken(token)) {
                return ResponseEntity.status(409).build();
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }
}
