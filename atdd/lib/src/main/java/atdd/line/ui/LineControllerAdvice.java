package atdd.line.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import atdd.common.ErrorResponse;
import atdd.station.exception.InputException;

@RestControllerAdvice
public class LineControllerAdvice {

    @ExceptionHandler(InputException.class)
    public ResponseEntity<ErrorResponse> lineNoExistException(InputException exception) {
        return getBadResponse(exception.getMessage());
    }

    public ResponseEntity<ErrorResponse> getBadResponse(String message) {
        ErrorResponse errResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(errResponse);
    }
}
