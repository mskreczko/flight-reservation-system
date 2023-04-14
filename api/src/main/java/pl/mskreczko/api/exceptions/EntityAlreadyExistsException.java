package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;
import pl.mskreczko.api.errors.ApiError;

import java.time.LocalDateTime;

public class EntityAlreadyExistsException extends RuntimeException {

    private final ApiError apiError;
    public EntityAlreadyExistsException() {
        super();
        apiError = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, "Entity already exists");
    }

    public ApiError getApiError() {
        return apiError;
    }
}
