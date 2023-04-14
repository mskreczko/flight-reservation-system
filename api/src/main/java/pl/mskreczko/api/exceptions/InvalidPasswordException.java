package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends ApiException {

    public InvalidPasswordException() {
        super(HttpStatus.CONFLICT, "Invalid password");
    }
}
