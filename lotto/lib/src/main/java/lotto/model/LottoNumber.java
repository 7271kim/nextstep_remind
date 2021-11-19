package lotto.model;

public class LottoNumber {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final String CHECK_VALIDATION = "입력이 정확하지 않습니다. 입력을 확인하세요";

    private int number;

    public LottoNumber(int number) {
        checkRange(number);
        this.number = number;
    }

    private void checkRange(int number) {
        if (number <= MIN_NUMBER || number >= MAX_NUMBER) {
            throw new IllegalArgumentException(CHECK_VALIDATION);
        }
    }

    public int getNumber() {
        return number;
    }

}
