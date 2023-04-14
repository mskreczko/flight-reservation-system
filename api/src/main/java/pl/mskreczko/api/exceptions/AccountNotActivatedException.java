package pl.mskreczko.api.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotActivatedException extends ApiException {

    public AccountNotActivatedException() {
        super(HttpStatus.CONFLICT, "Account has not been activated");
    }
}
