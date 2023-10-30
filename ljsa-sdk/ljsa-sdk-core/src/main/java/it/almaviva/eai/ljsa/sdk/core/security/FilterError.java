package it.almaviva.eai.ljsa.sdk.core.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class FilterError {

    private HttpStatus status;
    private String timestamp;
    private String message;
    private String debugMessage;
    private String path;

    private FilterError() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp = LocalDateTime.now().format(formatter);
    }

    FilterError(HttpStatus status) {
        this();
        this.status = status;
    }

     FilterError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.path = null;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

     FilterError(HttpStatus status, String message, String path, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.path = path;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
