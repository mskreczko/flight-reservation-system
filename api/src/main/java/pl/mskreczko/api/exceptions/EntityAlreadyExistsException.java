package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends ApiException {

    public EntityAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Entity already exists");
    }
}
