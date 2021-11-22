package lotto.model;

public class LottoPrice {
    private static final String CHECK_VALIDATION = "입력이 정확하지 않습니다. 입력을 확인하세요";
    private static final int LOTTO_PRICE = 1000;
    private int price;

    public LottoPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        checkUnit(price);
    }

    private void checkUnit(int price) {
        if (price == 0 || price % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(CHECK_VALIDATION);
        }
    }

    public static LottoPrice of(int price) {
        return new LottoPrice(price);
    }

    public int getLottoPrice() {
        return LOTTO_PRICE;
    }

    public int getCount() {
        return price / LOTTO_PRICE;
    }
}
