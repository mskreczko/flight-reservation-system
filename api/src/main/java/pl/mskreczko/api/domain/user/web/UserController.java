package pl.mskreczko.api.domain.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mskreczko.api.exceptions.NoSuchEntityException;
import pl.mskreczko.api.domain.user.service.UserService;

import java.util.UUID;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("details")
    public ResponseEntity<?> getUserDetails() {
        try {
            return ResponseEntity.ok(userService.getUserDetails(
                    UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())));
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            userService.deleteUser(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
            return ResponseEntity.noContent().build();
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(exception.getApiError(), exception.getApiError().getStatus());
        }
    }
}
