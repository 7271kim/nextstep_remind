package atdd.member.constant;

import java.util.Arrays;

public enum UserType {
    NORMAL(0),
    ADMIN(1);

    int code;

    private UserType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserType of(int code) {
        return Arrays.stream(UserType.values())
            .filter(type -> type.getCode() == code)
            .findAny()
            .orElse(NORMAL);
    }

}
