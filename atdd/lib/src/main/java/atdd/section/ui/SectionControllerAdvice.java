package atdd.section.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import atdd.common.ControllerAdvice;
import atdd.common.ErrorResponse;
import atdd.section.exception.AlreadyExistUpDownStationException;
import atdd.section.exception.DistanceLongException;

@RestControllerAdvice
public class SectionControllerAdvice extends ControllerAdvice {

    @ExceptionHandler(AlreadyExistUpDownStationException.class)
    public ResponseEntity<ErrorResponse> alreadyExistUpDownStationException(AlreadyExistUpDownStationException exception) {
        return getBadResponse(exception.getMessage());
    }

    @ExceptionHandler(DistanceLongException.class)
    public ResponseEntity<ErrorResponse> distanceLongException(DistanceLongException exception) {
        return getBadResponse(exception.getMessage());
    }

}
