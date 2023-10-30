package it.almaviva.eai.um.common.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DeleteNotAllowedException extends RuntimeException {

  public DeleteNotAllowedException(String exception) {
    super(exception);
  }
}
