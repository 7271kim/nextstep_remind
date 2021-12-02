package atdd.auth.exception;

public class AdminUserException extends RuntimeException {
    public static final String MESSAGE = "Admin 계정만 가능합니다.";
    private static final long serialVersionUID = 1L;

    public AdminUserException() {
        super(MESSAGE);
    }

}
