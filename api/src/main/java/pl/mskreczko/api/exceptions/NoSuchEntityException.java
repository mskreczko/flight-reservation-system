package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;
import pl.mskreczko.api.errors.ApiError;

import java.time.LocalDateTime;

public class NoSuchEntityException extends RuntimeException {

    private final ApiError apiError;
    public NoSuchEntityException() {
        super();
        apiError = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Entity not found");
    }

    public ApiError getApiError() {
        return apiError;
    }
}
