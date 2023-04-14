package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchEntityException extends ApiException {

    public NoSuchEntityException() {
        super(HttpStatus.NOT_FOUND, "Entity not found");
    }
}
