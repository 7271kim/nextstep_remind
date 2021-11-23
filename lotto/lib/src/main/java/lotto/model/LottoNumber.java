package lotto.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class LottoNumber implements Comparable<LottoNumber> {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final String CHECK_VALIDATION = "입력이 정확하지 않습니다. 입력을 확인하세요";

    private int number;

    public LottoNumber(String number) {
        chekNumber(number);
        init(Integer.parseInt(number));
    }

    public LottoNumber(int number) {
        init(number);
    }

    private void init(int number) {
        validate(number);
        this.number = number;
    }

    private void chekNumber(String number) {
        if (!Pattern.matches("^[0-9]+$", number)) {
            throw new IllegalArgumentException(CHECK_VALIDATION);
        }
    }

    private void validate(int number) {
        checkRange(number);
    }

    private void checkRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException(CHECK_VALIDATION);
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(LottoNumber compare) {
        return Integer.compare(this.number, compare.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LottoNumber other = (LottoNumber)obj;
        return number == other.number;
    }

}
