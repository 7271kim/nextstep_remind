package atdd.common;

public class InputException extends RuntimeException {
    public static final String MESSAGE = "잘못된 입력입니다.";
    private static final long serialVersionUID = 1L;

    public InputException() {
        super(MESSAGE);
    }

    public InputException(String message) {
        super(message);
    }
}
