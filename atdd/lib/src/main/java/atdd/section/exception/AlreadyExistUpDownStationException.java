package atdd.section.exception;

public class AlreadyExistUpDownStationException extends RuntimeException {
    public static final String MESSAGE = "이미 등록된 구간은 생성 불가능 합니다.";
    private static final long serialVersionUID = 1L;

    public AlreadyExistUpDownStationException() {
        super(MESSAGE);
    }
}
