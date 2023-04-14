package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;
import pl.mskreczko.api.errors.ApiError;

import java.time.LocalDateTime;

public abstract class ApiException extends RuntimeException {
    private final ApiError apiError;

    public ApiException(LocalDateTime timestamp, HttpStatus status, String message) {
        super();
        apiError = new ApiError(timestamp, status, message);
    }

    public ApiException(HttpStatus status, String message) {
        super();
        apiError = new ApiError(LocalDateTime.now(), status, message);
    }

    public ApiError getApiError() {
        return apiError;
    }
}
