package atdd.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InputException.class)
    public ResponseEntity<ErrorResponse> getInputException(InputException exception) {
        return getBadResponse(exception.getMessage());
    }

    public ResponseEntity<ErrorResponse> getBadResponse(String message) {
        ErrorResponse errResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(errResponse);
    }

    public ResponseEntity<ErrorResponse> getBadResponse(String message, HttpStatus httpStatus) {
        ErrorResponse errResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errResponse);
    }
}
