package atdd.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import atdd.auth.exception.AuthorizationException;
import atdd.common.ControllerAdvice;
import atdd.common.ErrorResponse;

@RestControllerAdvice
public class AuthorControllerAdvice extends ControllerAdvice {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> alreadyExistUpDownStationException(AuthorizationException exception) {
        return getBadResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
