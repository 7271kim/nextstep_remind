package atdd.auth.exception;

public class UserException extends RuntimeException {
    public static final String MESSAGE = "없는 유저 정보입니다.";
    private static final long serialVersionUID = 1L;

    public UserException() {
        super(MESSAGE);
    }

}
