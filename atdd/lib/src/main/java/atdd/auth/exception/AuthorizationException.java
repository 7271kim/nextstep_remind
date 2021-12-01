package atdd.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends RuntimeException {
    public static final String MESSAGE = "비밀번호를 확인하세요";
    private static final long serialVersionUID = 1L;

    public AuthorizationException() {
        super(MESSAGE);
    }

}
