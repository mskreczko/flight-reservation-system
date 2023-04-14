package pl.mskreczko.api.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiError {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
}
