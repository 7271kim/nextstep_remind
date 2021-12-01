package atdd.member.constant;

import java.util.Arrays;

public enum ActiveType {
    READY(0),
    ACTIVE(1),
    STOP(2),
    REMOVED(3),
    BLOCK(4);

    int code;

    private ActiveType(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ActiveType of(int code) {
        return Arrays.stream(ActiveType.values())
            .filter(type -> type.getCode() == code)
            .findAny().orElse(ACTIVE);
    }

}
