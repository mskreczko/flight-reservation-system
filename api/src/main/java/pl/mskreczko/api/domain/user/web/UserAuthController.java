package pl.mskreczko.api.domain.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.api.domain.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.api.domain.user.dto.UserLoginDto;
import pl.mskreczko.api.domain.user.dto.UserRegistrationDto;
import pl.mskreczko.api.domain.user.service.UserAuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    private final UserAuthService userAuthService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            final var token = userAuthService.loginUser(userLoginDto);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            userAuthService.registerUser(userRegistrationDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (EntityAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }
}
