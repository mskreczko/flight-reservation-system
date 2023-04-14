package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;
import pl.mskreczko.api.errors.ApiError;

import java.time.LocalDateTime;

public class AccountNotActivatedException extends RuntimeException {

    private final ApiError apiError;
    public AccountNotActivatedException() {
        super();
        apiError = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, "Account has not been activated");
    }

    public ApiError getApiError() {
        return apiError;
    }
}
