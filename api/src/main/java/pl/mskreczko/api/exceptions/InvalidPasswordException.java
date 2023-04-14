package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;
import pl.mskreczko.api.errors.ApiError;

import java.time.LocalDateTime;

public class InvalidPasswordException extends RuntimeException {

    private final ApiError apiError;
    public InvalidPasswordException() {
        super();
        apiError = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, "Invalid password");
    }

    public ApiError getApiError() {
        return apiError;
    }
}
