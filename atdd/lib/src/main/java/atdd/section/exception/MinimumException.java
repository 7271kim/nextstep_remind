package atdd.section.exception;

public class MinimumException extends RuntimeException {
    public static final String MESSAGE = "최소 1개의 구간은 존재해야 합니다.";
    private static final long serialVersionUID = 1L;

    public MinimumException() {
        super(MESSAGE);
    }

}
