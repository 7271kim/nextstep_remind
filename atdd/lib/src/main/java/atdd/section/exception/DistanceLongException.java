package atdd.section.exception;

public class DistanceLongException extends RuntimeException {
    public static final String MESSAGE = "기존 등록된 구간보다 거리가 큽니다. 거리를 확인하세요";
    private static final long serialVersionUID = 1L;

    public DistanceLongException() {
        super(MESSAGE);
    }

}
