package it.almaviva.eai.ljsa.sdk.core.throwable;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JWTHeaderException extends RuntimeException {

    public JWTHeaderException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
